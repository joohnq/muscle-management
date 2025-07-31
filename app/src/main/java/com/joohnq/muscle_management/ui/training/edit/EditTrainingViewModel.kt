package com.joohnq.muscle_management.ui.training.edit

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.use_case.training.GetByIdTrainingUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import com.joohnq.muscle_management.ui.training.add.AddTrainingContract
import kotlinx.coroutines.launch

class EditTrainingViewModel(
    private val getByIdTrainingUseCase: GetByIdTrainingUseCase,
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
            try {
                val result = getByIdTrainingUseCase(id).getOrThrow()

                updateState {
                    it.copy(
                        training = result.first,
                        exercises = result.second
                    )
                }
            } catch (e: Exception) {
                updateState { it.copy(isError = e) }
            }
        }
    }

    private fun updateTraining() {

    }
}