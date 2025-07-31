package com.joohnq.muscle_management.ui.training

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingScreen(
    padding: PaddingValues,
    onNavigateToAddTraining: () -> Unit,
    viewModel: TrainingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onEvent(event: TrainingContract.Event) {
        when (event) {
            TrainingContract.Event.AddTraining -> onNavigateToAddTraining()
        }
    }

    TrainingContent(
        padding = padding,
        state = state,
        onEvent = ::onEvent
    )
}