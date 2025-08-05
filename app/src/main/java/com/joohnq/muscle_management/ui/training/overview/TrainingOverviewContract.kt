package com.joohnq.muscle_management.ui.training.overview

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

sealed interface TrainingOverviewContract {
    sealed interface Event {
        data object NavigateToAddTraining : Event
        data class NavigateToEditTraining(val id: String) : Event
    }

    sealed interface Intent{
        data object GetAll: Intent
        data object SignOut: Intent
        data class Delete(val id: String): Intent
    }

    sealed interface SideEffect{
        data object NavigateToSignIn: SideEffect
        data class ShowError(val message: String): SideEffect
    }

    data class State(
        val trainings: List<Pair<Training, List<Exercise>>> = listOf(),
        val isLoading: Boolean = false,
        val isError: Throwable? = null
    )
}