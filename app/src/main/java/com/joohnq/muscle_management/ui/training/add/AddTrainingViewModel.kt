package com.joohnq.muscle_management.ui.training.add

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.exception.TrainingException
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.use_case.training.AddTrainingUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class AddTrainingViewModel(
    private val addTrainingUseCase: AddTrainingUseCase,
    initialState: AddTrainingContract.State = AddTrainingContract.State(),
) : BaseViewModel<AddTrainingContract.State, AddTrainingContract.Intent, AddTrainingContract.SideEffect>(
    initialState = initialState
) {
    override fun onIntent(intent: AddTrainingContract.Intent) {
        when (intent) {
            is AddTrainingContract.Intent.ChangeTrainingDescription -> {
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

            is AddTrainingContract.Intent.ChangeTrainingName -> {
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

            AddTrainingContract.Intent.AddExercise -> {
                updateState {
                    it.copy(
                        trainingState = it.trainingState.copy(
                            exercises = state.value.trainingState.exercises.plus(Exercise()),
                        )
                    )
                }
            }

            is AddTrainingContract.Intent.ToggleExerciseEdit -> {
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

            is AddTrainingContract.Intent.ChangeExerciseImage -> {
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

            is AddTrainingContract.Intent.ChangeExerciseName -> {
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

            is AddTrainingContract.Intent.ChangeExerciseObservations -> {
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

            is AddTrainingContract.Intent.DeleteExercise -> {
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

            AddTrainingContract.Intent.AddTraining -> addTraining()
        }
    }

    private fun addTraining() {
        if (
            !state.value.trainingState.trainingNameError.isNullOrBlank() ||
            !state.value.trainingState.trainingDescriptionError.isNullOrBlank() ||
            !state.value.trainingState.editingExerciseNameError.isNullOrBlank() ||
            !state.value.trainingState.editingExerciseImageError.isNullOrBlank()
        )
            return

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            try {
                addTrainingUseCase(
                    training = state.value.trainingState.training,
                    exercises = state.value.trainingState.exercises
                ).getOrThrow()

                emitEffect(AddTrainingContract.SideEffect.NavigateBack)
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
                emitEffect(AddTrainingContract.SideEffect.ShowError(e.message.toString()))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}