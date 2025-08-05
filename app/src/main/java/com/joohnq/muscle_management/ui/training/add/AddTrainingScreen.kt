package com.joohnq.muscle_management.ui.training.add

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddTrainingScreen(
    onGoBack: () -> Unit,
    viewModel: AddTrainingViewModel = koinViewModel(),
) {
    val snackBarState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onEvent(event: AddTrainingContract.Event) {
        when (event) {
            AddTrainingContract.Event.OnGoBack -> onGoBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is AddTrainingContract.SideEffect.NavigateBack -> onGoBack()

                is AddTrainingContract.SideEffect.ShowError ->
                    snackBarState.showSnackbar(sideEffect.message)
            }
        }
    }

    AddTrainingContent(
        snackBarState = snackBarState,
        state = state,
        onIntent = viewModel::onIntent,
        onEvent = ::onEvent
    )
}