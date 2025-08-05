package com.joohnq.muscle_management.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    navigateToSignIn: () -> Unit,
    navigateToTrainingOverview: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.onIntent(SplashContract.Intent.GetUserId)

        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SplashContract.SideEffect.NavigateToSignIn -> navigateToSignIn()
                is SplashContract.SideEffect.NavigateToTrainingOverview -> navigateToTrainingOverview()
            }
        }
    }

    SplashContent()
}