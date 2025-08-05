package com.joohnq.muscle_management.ui.training.edit

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.exception.TrainingException
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.use_case.training.GetByIdTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.UpdateTrainingUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class EditTrainingViewModel(
    private val getByIdTrainingUseCase: GetByIdTrainingUseCase,
    private val updateTrainingUseCase: UpdateTrainingUseCase,
    initialState: EditTrainingContract.State = EditTrainingContract.State(),
) : BaseViewModel<EditTrainingContract.State, EditTrainingContract.Intent, EditTrainingContract.SideEffect>(
    initialState = initialState
) {
    override fun onIntent(intent: EditTrainingContract.Intent) {
        when (intent) {
            is EditTrainingContract.Intent.GetTraining -> getTraining(intent.id)

            EditTrainingContract.Intent.UpdateTraining -> updateTraining()

            is EditTrainingContract.Intent.ChangeTrainingDescription -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            training = state.value.trainingState.training.copy(
                                description = intent.description
                            ),
                            trainingDescriptionError = null
                        )
                    )
                }
            }

            is EditTrainingContract.Intent.ChangeTrainingName -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            training = state.value.trainingState.training.copy(
                                name = intent.name
                            ),
                            trainingNameError = null
                        )
                    )
                }
            }

            EditTrainingContract.Intent.AddExercise -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            exercises = state.value.trainingState.exercises.plus(Exercise()),
                        )
                    )
                }
            }

            is EditTrainingContract.Intent.ToggleExerciseEdit -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            editingExerciseId = if (it.trainingState.editingExerciseId == intent.id) {
                                null
                            } else {
                                intent.id
                            }
                        )
                    )
                }
            }

            is EditTrainingContract.Intent.ChangeExerciseImage -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            exercises = it.trainingState.exercises.map { exercise ->
                                if (exercise.id == intent.id) {
                                    exercise.copy(image = intent.image)
                                } else {
                                    exercise
                                }
                            },
                            editingExerciseImageError = null
                        )
                    )
                }
            }

            is EditTrainingContract.Intent.ChangeExerciseName -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            exercises = it.trainingState.exercises.map { exercise ->
                                if (exercise.id == intent.id) {
                                    exercise.copy(name = intent.name)
                                } else {
                                    exercise
                                }
                            },
                            editingExerciseNameError = null
                        )
                    )
                }
            }

            is EditTrainingContract.Intent.ChangeExerciseObservations -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            exercises = it.trainingState.exercises.map { exercise ->
                                if (exercise.id == intent.id) {
                                    exercise.copy(observations = intent.observations)
                                } else {
                                    exercise
                                }
                            },
                        )
                    )
                }
            }

            is EditTrainingContract.Intent.DeleteExercise -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            exercises = it.trainingState.exercises.filter { exercise ->
                                exercise.id != intent.id
                            }
                        )
                    )
                }
            }
        }
    }

    private fun getTraining(id: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            try {
                val result = getByIdTrainingUseCase(id).getOrThrow()

                updateState {
                    it.copy(
                        it.trainingState.copy(
                            training = result.first,
                            exercises = result.second
                        ),
                    )
                }
            } catch (e: Exception) {
                updateState { it.copy(isError = e) }
            } finally {
                updateState { it.copy(isLoading = false) }

            }
        }
    }

    private fun updateTraining() {
        if (!state.value.trainingState.trainingNameError.isNullOrBlank() || !state.value.trainingState.trainingDescriptionError.isNullOrBlank())
            return

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            try {
                updateTrainingUseCase(
                    training = state.value.trainingState.training,
                    exercises = state.value.trainingState.exercises
                ).getOrThrow()

                emitEffect(EditTrainingContract.SideEffect.NavigateBack)
            } catch (e: ValidationException) {
                e.errors.forEach { error ->
                    when (error) {
                        TrainingException.EmptyTrainingName ->
                            updateState {
                                it.copy(
                                    trainingState = it.trainingState.copy(
                                        trainingNameError = "O nome do treino é obrigatório"
                                    )
                                )
                            }

                        is TrainingException.InvalidExerciseName ->
                            updateState {
                                it.copy(
                                    trainingState = it.trainingState.copy(
                                        editingExerciseNameError = "O nome do exercício é obrigatório",
                                        editingExerciseErrorId = error.id
                                    )
                                )
                            }

                        is TrainingException.InvalidExerciseImage ->
                            updateState {
                                it.copy(
                                    trainingState = it.trainingState.copy(
                                        editingExerciseImageError = "A URL da imagem é inválida",
                                        editingExerciseErrorId = error.id
                                    )
                                )
                            }
                    }
                }
            } catch (e: Exception) {
                emitEffect(EditTrainingContract.SideEffect.ShowError(e.message.toString()))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}