package com.joohnq.muscle_management.ui.training.overview

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

sealed interface TrainingOverviewContract {
    sealed interface Event {
        data object AddTraining : Event
        data object Retry : Event
        data class EditTraining(val id: String) : Event
        data class AddExercise(val id: String) : Event
    }

    sealed interface Intent{
        data object GetAll: Intent
        data class Delete(val id: String): Intent
    }

    sealed interface SideEffect{
        data class ShowError(val error: Throwable): SideEffect
    }

    data class State(
        val trainings: List<Pair<Training, List<Exercise>>> = listOf(),
        val isLoading: Boolean = false,
        val isError: Throwable? = null
    )
}