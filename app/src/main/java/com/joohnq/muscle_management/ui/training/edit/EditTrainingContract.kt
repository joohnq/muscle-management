package com.joohnq.muscle_management.ui.training.edit

import com.joohnq.muscle_management.ui.training.TrainingContract

sealed interface EditTrainingContract {
    sealed interface Event {
        data object OnGoBack : Event
    }

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data class GetTraining(val id: String) :
            Intent

        data object UpdateTraining : Intent

        data class ChangeTrainingName(val name: String) : Intent
        data class ChangeTrainingDescription(val description: String) : Intent
        data class ChangeExerciseName(val id: String, val name: String) : Intent
        data class ChangeExerciseImage(val id: String, val image: String) :
            Intent

        data class ChangeExerciseObservations(val id: String, val observations: String) :
            Intent

        data class DeleteExercise(val id: String) : Intent
        data class ToggleExerciseEdit(val id: String) : Intent
        data object AddExercise : Intent
    }

    data class State(
        val trainingState: TrainingContract.State = TrainingContract.State(),
        val isLoading: Boolean = false,
        val isError: Throwable? = null,
    )
}