package com.joohnq.muscle_management.ui.training.edit

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.exception.TrainingException
import com.joohnq.muscle_management.domain.use_case.training.GetByIdTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.UpdateTrainingUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class EditTrainingViewModel(
    private val getByIdTrainingUseCase: GetByIdTrainingUseCase,
    private val updateTrainingUseCase: UpdateTrainingUseCase,
    initialState: EditTrainingContract.State = EditTrainingContract.State()
) : BaseViewModel<EditTrainingContract.State, EditTrainingContract.Intent, EditTrainingContract.SideEffect>(
    initialState = initialState
) {
    override fun onIntent(intent: EditTrainingContract.Intent) {
        when (intent) {
            is EditTrainingContract.Intent.GetTraining -> getTraining(intent.id)

            EditTrainingContract.Intent.UpdateTraining -> updateTraining()

            is EditTrainingContract.Intent.UpdateTrainingDescription -> {
                updateState {
                    it.copy(
                        training = state.value.training.copy(
                            description = intent.description
                        ),
                        trainingDescriptionError = null
                    )
                }
            }

            is EditTrainingContract.Intent.UpdateTrainingName -> {
                updateState {
                    it.copy(
                        training = state.value.training.copy(
                            name = intent.name
                        ),
                        trainingNameError = null
                    )
                }
            }

            EditTrainingContract.Intent.AddExercise -> {
                updateState {
                    it.copy(
                        exercises = state.value.exercises.plus(
                            Exercise()
                        )
                    )
                }
            }

            is EditTrainingContract.Intent.ToggleExerciseEdit -> {
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

            is EditTrainingContract.Intent.UpdateExerciseImage -> {
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

            is EditTrainingContract.Intent.UpdateExerciseName -> {
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

            is EditTrainingContract.Intent.UpdateExerciseObservations -> {
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

            is EditTrainingContract.Intent.DeleteExercise -> {
                updateState {
                    it.copy(
                        exercises = it.exercises.filter { exercise ->
                            exercise.id != intent.id
                        }
                    )
                }
            }
        }
    }

    private fun getTraining(id: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val result = getByIdTrainingUseCase(id).getOrElse { error ->
                updateState { it.copy(isError = error) }
                return@launch
            }

            updateState {
                it.copy(
                    training = result.first,
                    exercises = result.second
                )
            }

            updateState { it.copy(isLoading = false) }
        }
    }

    private fun updateTraining() {
        if (!state.value.trainingNameError.isNullOrBlank() || !state.value.trainingDescriptionError.isNullOrBlank())
            return

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            try {
                updateTrainingUseCase(state.value.training, state.value.exercises)
                    .getOrThrow()

                emitEffect(EditTrainingContract.SideEffect.NavigateBack)
            } catch (_: TrainingException.EmptyTrainingName) {
                updateState {
                    it.copy(
                        trainingNameError = "O nome do treino é obrigatório"
                    )
                }
            } catch (error: TrainingException.InvalidExerciseName) {
                updateState {
                    it.copy(
                        editingExerciseNameError = "O nome do exercício é obrigatório",
                        editingExerciseErrorId = error.id
                    )
                }
            } catch (error: TrainingException.InvalidExerciseImage) {
                updateState {
                    it.copy(
                        editingExerciseImageError = "A URL da imagem é inválida",
                        editingExerciseErrorId = error.id
                    )
                }
            } catch (error: Exception) {
                emitEffect(EditTrainingContract.SideEffect.ShowError(error))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}