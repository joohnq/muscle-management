package com.joohnq.muscle_management.ui.training.add

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

sealed interface AddTrainingContract {
    sealed interface Event {
        data object OnGoBack : Event
    }

    sealed interface SideEffect{
        data object NavigateBack : SideEffect
        data class ShowError(val error: Throwable) : SideEffect
    }

    sealed interface Intent {
        data class UpdateTrainingName(val name: String) : Intent
        data class UpdateTrainingDescription(val description: String) : Intent
        data class UpdateExerciseName(val id: String, val name: String) : Intent
        data class UpdateExerciseImage(val id: String, val image: String) : Intent
        data class UpdateExerciseObservations(val id: String, val observations: String) : Intent
        data class DeleteExercise(val id: String) : Intent
        data class ToggleExerciseEdit(val id: String) : Intent
        data object AddExercise: Intent
        data object AddTraining: Intent
    }

    data class State(
        val training: Training = Training(),
        val exercises: List<Exercise> = listOf(),
        val isLoading: Boolean = false,
        val trainingNameError: String? = null,
        val trainingDescriptionError: String? = null,
        val editingExerciseId: String? = null,
    )
}