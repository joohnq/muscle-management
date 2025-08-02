package com.joohnq.muscle_management.ui.auth.sign_in

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.use_case.auth.SignInUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    initialState: SignInContract.State = SignInContract.State()
) : BaseViewModel<SignInContract.State, SignInContract.Intent, SignInContract.SideEffect>(
    initialState = initialState
) {
    override fun onIntent(intent: SignInContract.Intent) {
        when (intent) {
            SignInContract.Intent.SignIn -> signIn()
            is SignInContract.Intent.UpdateEmail -> {
                updateState {
                    it.copy(email = it.email.copy(value = intent.email, error = null))
                }
            }

            is SignInContract.Intent.UpdateIsPasswordVisible -> {
                updateState {
                    it.copy(isPasswordVisible = intent.visible)
                }
            }

            is SignInContract.Intent.UpdatePassword -> {
                updateState {
                    it.copy(password = it.password.copy(value = intent.password, error = null))
                }
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            signInUseCase(
                email = state.value.email.value,
                password = state.value.password.value
            ).getOrElse { error ->
                emitEffect(SignInContract.SideEffect.ShowError(error))
            }

            emitEffect(SignInContract.SideEffect.NavigateNext)

            updateState { it.copy(isLoading = false) }
        }
    }
}