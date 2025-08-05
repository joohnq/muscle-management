package com.joohnq.muscle_management.ui.training.overview

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingOverviewScreen(
    onNavigateToAddTraining: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToEditTraining: (String) -> Unit,
    viewModel: TrainingOverviewViewModel = koinViewModel(),
) {
    val snackBarState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onEvent(event: TrainingOverviewContract.Event) {
        when (event) {
            TrainingOverviewContract.Event.NavigateToAddTraining ->
                onNavigateToAddTraining()

            is TrainingOverviewContract.Event.NavigateToEditTraining ->
                onNavigateToEditTraining(event.id)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(TrainingOverviewContract.Intent.GetAll)

        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                TrainingOverviewContract.SideEffect.NavigateToSignIn ->
                    onNavigateToSignIn()

                is TrainingOverviewContract.SideEffect.ShowError ->
                    snackBarState.showSnackbar(sideEffect.message)
            }
        }
    }

    TrainingOverviewContent(
        snackBarState = snackBarState,
        state = state,
        onEvent = ::onEvent,
        onIntent = viewModel::onIntent
    )
}