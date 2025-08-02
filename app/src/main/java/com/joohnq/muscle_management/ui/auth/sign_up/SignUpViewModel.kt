package com.joohnq.muscle_management.ui.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.use_case.auth.SignUpUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
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
                    it.copy(email = it.email.copy(value = intent.email, error = null))
                }
            }

            is SignUpContract.Intent.UpdateIsPasswordVisible -> {
                updateState {
                    it.copy(isPasswordVisible = intent.visible)
                }
            }

            is SignUpContract.Intent.UpdatePassword -> {
                updateState {
                    it.copy(password = it.password.copy(value = intent.password, error = null))
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            signUpUseCase(
                email = state.value.email.value,
                password = state.value.password.value
            ).getOrElse { error ->
                emitEffect(SignUpContract.SideEffect.ShowError(error))
            }

            emitEffect(SignUpContract.SideEffect.NavigateNext)

            updateState { it.copy(isLoading = false) }
        }
    }
}