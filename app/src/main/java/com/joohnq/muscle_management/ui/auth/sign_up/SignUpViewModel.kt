package com.joohnq.muscle_management.ui.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.exception.AuthException
import com.joohnq.muscle_management.domain.exception.ValidationException
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
            is SignUpContract.Intent.ChangeEmail -> {
                updateState {
                    it.copy(email = it.email.copy(value = intent.email, error = null))
                }
            }

            is SignUpContract.Intent.ChangeIsPasswordVisible -> {
                updateState {
                    it.copy(isPasswordVisible = intent.visible)
                }
            }

            is SignUpContract.Intent.ChangePassword -> {
                updateState {
                    it.copy(password = it.password.copy(value = intent.password, error = null))
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            try {
                signUpUseCase(
                    email = state.value.email.value,
                    password = state.value.password.value
                ).getOrThrow()

                emitEffect(SignUpContract.SideEffect.NavigateNext)
            } catch (e: ValidationException) {
                e.errors.forEach { error ->
                    when (error) {
                        AuthException.EmptyEmailException -> updateState {
                            it.copy(email = it.email.copy(error = error.message))
                        }

                        is AuthException.InvalidEmailException -> updateState {
                            it.copy(email = it.email.copy(error = error.message))
                        }

                        AuthException.EmptyPasswordException -> updateState {
                            it.copy(password = it.password.copy(error = error.message))
                        }
                    }
                }
            } catch (e: Exception) {
                emitEffect(SignUpContract.SideEffect.ShowError(e.message.toString()))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}