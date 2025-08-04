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

            val id = getUserIdUseCase().getOrElse { error ->
                updateState { it.copy(isError = error) }
            }

            if (id == null) {
                emitEffect(SplashContract.SideEffect.NavigateToSignIn)
            } else {
                emitEffect(SplashContract.SideEffect.NavigateToTrainingOverview)
            }

            updateState { it.copy(isLoading = false) }
        }
    }
}