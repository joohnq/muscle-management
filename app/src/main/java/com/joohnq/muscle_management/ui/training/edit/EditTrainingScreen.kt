package com.joohnq.muscle_management.ui.training.edit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EditTrainingScreen(
    id: String,
    onGoBack: () -> Unit,
    viewModel: EditTrainingViewModel = koinViewModel(),
) {
    val snackBarState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onEvent(event: EditTrainingContract.Event) {
        when (event) {
            EditTrainingContract.Event.OnGoBack -> onGoBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(EditTrainingContract.Intent.GetTraining(id))

        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is EditTrainingContract.SideEffect.NavigateBack -> onGoBack()

                is EditTrainingContract.SideEffect.ShowError ->
                    snackBarState.showSnackbar(sideEffect.message)
            }
        }
    }

    EditTrainingContent(
        snackBarState = snackBarState,
        state = state,
        onIntent = viewModel::onIntent,
        onEvent = ::onEvent
    )
}