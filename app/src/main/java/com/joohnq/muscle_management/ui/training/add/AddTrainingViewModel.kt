package com.joohnq.muscle_management.ui.training.add

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.use_case.training.AddTrainingUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class AddTrainingViewModel(
    private val addTrainingUseCase: AddTrainingUseCase,
    initialState: AddTrainingContract.State = AddTrainingContract.State()
) : BaseViewModel<AddTrainingContract.State, AddTrainingContract.Intent, AddTrainingContract.SideEffect>(
    initialState = initialState
) {
    override fun onIntent(intent: AddTrainingContract.Intent) {
        when (intent) {
            is AddTrainingContract.Intent.UpdateTrainingDescription -> {
                updateState {
                    it.copy(
                        training = state.value.training.copy(
                            description = intent.description
                        ),
                        trainingDescriptionError = null
                    )
                }
            }

            is AddTrainingContract.Intent.UpdateTrainingName -> {
                updateState {
                    it.copy(
                        training = state.value.training.copy(
                            name = intent.name
                        ),
                        trainingNameError = null
                    )
                }
            }

            AddTrainingContract.Intent.AddExercise -> {
                updateState {
                    it.copy(
                        exercises = state.value.exercises.plus(
                            Exercise()
                        )
                    )
                }
            }

            is AddTrainingContract.Intent.ToggleExerciseEdit -> {
                updateState {
                    it.copy(
                        editingExerciseId = if (it.editingExerciseId == intent.id) {
                            null
                        } else {
                            intent.id
                        }
                    )
                }
            }

            is AddTrainingContract.Intent.UpdateExerciseImage -> {
                updateState {
                    it.copy(
                        exercises = it.exercises.map { exercise ->
                            if (exercise.id == intent.id) {
                                exercise.copy(image = intent.image)
                            } else {
                                exercise
                            }
                        }
                    )
                }
            }

            is AddTrainingContract.Intent.UpdateExerciseName -> {
                updateState {
                    it.copy(
                        exercises = it.exercises.map { exercise ->
                            if (exercise.id == intent.id) {
                                exercise.copy(name = intent.name)
                            } else {
                                exercise
                            }
                        }
                    )
                }
            }

            is AddTrainingContract.Intent.UpdateExerciseObservations -> {
                updateState {
                    it.copy(
                        exercises = it.exercises.map { exercise ->
                            if (exercise.id == intent.id) {
                                exercise.copy(observations = intent.observations)
                            } else {
                                exercise
                            }
                        }
                    )
                }
            }

            is AddTrainingContract.Intent.DeleteExercise -> {
                updateState {
                    it.copy(
                        exercises = it.exercises.filter { exercise ->
                            exercise.id != intent.id
                        }
                    )
                }
            }

            AddTrainingContract.Intent.AddTraining -> addTraining()
        }
    }

    private fun addTraining() {
        validateTraining()

        if (!state.value.trainingNameError.isNullOrBlank() || !state.value.trainingDescriptionError.isNullOrBlank())
            return

        viewModelScope.launch {
            try {
                updateState { it.copy(isLoading = true)}
                executeTrainingUseCase()

                emitEffect(AddTrainingContract.SideEffect.NavigateBack)
            } catch (e: Exception) {
                emitEffect(AddTrainingContract.SideEffect.ShowError(e))

                return@launch
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateTraining() {
        if (state.value.training.name == "") {
            updateState {
                it.copy(
                    trainingNameError = "O nome do treino é obrigatório"
                )
            }
        }

        if (state.value.training.description == "") {
            updateState {
                it.copy(
                    trainingDescriptionError = "A descrição do treino é obrigatório"
                )
            }
        }
    }

    private suspend fun executeTrainingUseCase() {
        val trainingResult = addTrainingUseCase(state.value.training, state.value.exercises)

        return trainingResult.getOrElse { throw it }
    }
}