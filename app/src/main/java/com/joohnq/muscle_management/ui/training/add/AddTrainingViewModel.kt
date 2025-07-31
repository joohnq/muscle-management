package com.joohnq.muscle_management.ui.training.add

import android.util.Patterns
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
                        },
                        editingExerciseImageError = null
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
                        },
                        editingExerciseNameError = null
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
                        },
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
        validateExercises()

        if (
            !state.value.trainingNameError.isNullOrBlank() ||
            !state.value.trainingDescriptionError.isNullOrBlank() ||
            !state.value.editingExerciseNameError.isNullOrBlank() ||
            !state.value.editingExerciseImageError.isNullOrBlank()
        )
            return

        viewModelScope.launch {
            try {
                updateState { it.copy(isLoading = true) }
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
    }

    private fun validateExercises() {
        state.value.exercises.forEach { exercise ->
            if (exercise.name == "") {
                updateState {
                    it.copy(
                        editingExerciseNameError = "O nome do exercício é obrigatório",
                        editingExerciseErrorId = exercise.id
                    )
                }
            }

            if (exercise.image.isNotBlank()) {
                if (
                    (!exercise.image.startsWith("http://") ||
                            !exercise.image.startsWith("https://"))
                    && !Patterns.WEB_URL.matcher(exercise.image).matches()
                ) {
                    updateState {
                        it.copy(
                            editingExerciseImageError = "A URL da imagem é inválida",
                            editingExerciseErrorId = exercise.id
                        )
                    }
                }
            }
        }
    }

    private suspend fun executeTrainingUseCase() {
        val trainingResult = addTrainingUseCase(state.value.training, state.value.exercises)

        return trainingResult.getOrThrow()
    }
}