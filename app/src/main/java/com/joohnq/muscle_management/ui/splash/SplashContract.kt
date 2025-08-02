package com.joohnq.muscle_management.ui.splash

sealed interface SplashContract {
    sealed interface Event

    sealed interface SideEffect{
        data object NavigateToSignIn: SideEffect
        data object NavigateToTrainingOverview: SideEffect
    }

    sealed interface Intent{
        data object GetUserId: Intent
    }

    data class State(
        val isLoading: Boolean = false,
        val isError: Throwable? = null,
    )
}