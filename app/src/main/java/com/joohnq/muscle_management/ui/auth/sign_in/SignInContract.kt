package com.joohnq.muscle_management.ui.auth.sign_in

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

sealed interface SignInContract {
    sealed interface Event {
        data object NavigateSignUp : Event
        data object CloseKeyboard : Event
    }

    sealed interface SideEffect {
        data object NavigateNext : SideEffect
        data class ShowError(val error: Throwable) : SideEffect
    }

    sealed interface Intent {
        data class UpdateEmail(val email: String) : Intent
        data class UpdatePassword(val password: String) : Intent
        data class UpdateIsPasswordVisible(val visible: Boolean) : Intent
        data object SignIn : Intent
    }

    data class State(
        val email: String = "",
        val emailError: String? = null,
        val password: String = "",
        val passwordError: String? = null,
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Throwable? = null,
    )
}