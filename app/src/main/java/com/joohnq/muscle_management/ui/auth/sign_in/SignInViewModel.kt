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
                    it.copy(email = intent.email)
                }
            }

            is SignInContract.Intent.UpdateIsPasswordVisible -> {
                updateState {
                    it.copy(isPasswordVisible = intent.visible)
                }
            }

            is SignInContract.Intent.UpdatePassword -> {
                updateState {
                    it.copy(password = intent.password)
                }
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            try {
                updateState { it.copy(isLoading = true) }

                signInUseCase(
                    email = state.value.email,
                    password = state.value.password
                ).getOrThrow()

                emitEffect(SignInContract.SideEffect.NavigateNext)
            } catch (e: Exception) {
                emitEffect(SignInContract.SideEffect.ShowError(e))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}