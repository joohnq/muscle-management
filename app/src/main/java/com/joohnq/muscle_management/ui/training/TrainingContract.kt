package com.joohnq.muscle_management.ui.training

sealed interface TrainingContract {
    sealed interface Event{
        data object AddTraining: Event
    }
}