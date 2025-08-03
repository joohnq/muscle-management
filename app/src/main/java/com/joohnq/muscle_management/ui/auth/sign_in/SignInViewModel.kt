package com.joohnq.muscle_management.ui.auth.sign_in

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.exception.AuthException
import com.joohnq.muscle_management.domain.exception.ValidationException
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

            try {
                signInUseCase(
                    email = state.value.email.value,
                    password = state.value.password.value
                ).getOrThrow()

                emitEffect(SignInContract.SideEffect.NavigateNext)
            } catch (e: ValidationException) {
                e.errors.forEach { error ->
                    when (error) {
                        is AuthException.EmptyEmailException ->
                            updateState {
                                it.copy(email = it.email.copy(error = error.message))
                            }

                        is AuthException.InvalidEmailException ->
                            updateState {
                                it.copy(email = it.email.copy(error = error.message))
                            }

                        is AuthException.EmptyPasswordException ->
                            updateState {
                                it.copy(password = it.password.copy(error = error.message))
                            }
                    }
                }
            } catch (e: Exception) {
                emitEffect(SignInContract.SideEffect.ShowError(e))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}