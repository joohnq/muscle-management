package com.joohnq.muscle_management.ui.splash

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.use_case.auth.GetUserIdUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getUserIdUseCase: GetUserIdUseCase,
    initialState: SplashContract.State = SplashContract.State(),
) : BaseViewModel<SplashContract.State, SplashContract.Intent, SplashContract.SideEffect>(
    initialState = initialState
) {
    override fun onIntent(intent: SplashContract.Intent) {
        when (intent) {
            SplashContract.Intent.GetUserId -> getUserId()
        }
    }

    private fun getUserId() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            try {
                val id = getUserIdUseCase().getOrThrow()

                val sideEffect =
                    if (id == null) {
                        SplashContract.SideEffect.NavigateToSignIn
                    } else {
                        SplashContract.SideEffect.NavigateToTrainingOverview
                    }

                emitEffect(sideEffect)
            } catch (e: Exception) {
                updateState { it.copy(isError = e) }
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}