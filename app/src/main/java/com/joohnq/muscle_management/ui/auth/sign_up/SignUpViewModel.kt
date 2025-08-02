package com.joohnq.muscle_management.ui.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.use_case.auth.SignUpUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import com.joohnq.muscle_management.ui.auth.sign_in.SignInContract
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    initialState: SignUpContract.State = SignUpContract.State()
) : BaseViewModel<SignUpContract.State, SignUpContract.Intent, SignUpContract.SideEffect>(
    initialState = initialState
) {
    override fun onIntent(intent: SignUpContract.Intent) {
        when (intent) {
            SignUpContract.Intent.SignUp -> signUp()
            is SignUpContract.Intent.UpdateEmail -> {
                updateState {
                    it.copy(email = intent.email)
                }
            }

            is SignUpContract.Intent.UpdateIsPasswordVisible -> {
                updateState {
                    it.copy(isPasswordVisible = intent.visible)
                }
            }

            is SignUpContract.Intent.UpdatePassword -> {
                updateState {
                    it.copy(password = intent.password)
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            try {
                updateState { it.copy(isLoading = true) }

                signUpUseCase(
                    email = state.value.email,
                    password = state.value.password
                ).getOrThrow()

                emitEffect(SignUpContract.SideEffect.NavigateNext)
            } catch (e: Exception) {
                emitEffect(SignUpContract.SideEffect.ShowError(e))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}