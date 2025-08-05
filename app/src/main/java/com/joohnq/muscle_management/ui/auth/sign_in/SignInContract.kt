package com.joohnq.muscle_management.ui.auth.sign_in

import com.joohnq.muscle_management.domain.entity.Field

sealed interface SignInContract {
    sealed interface Event {
        data object NavigateSignUp : Event
        data object CloseKeyboard : Event
    }

    sealed interface SideEffect {
        data object NavigateNext : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data class ChangeEmail(val email: String) : Intent
        data class ChangePassword(val password: String) : Intent
        data class ChangeIsPasswordVisible(val visible: Boolean) : Intent
        data object SignIn : Intent
    }

    data class State(
        val email: Field = Field(""),
        val password: Field = Field(""),
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false,
    )
}