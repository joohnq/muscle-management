package com.joohnq.muscle_management.ui.exercise

sealed interface ExerciseContract {
    sealed interface Event{
        data object AddTraining: Event
    }
}