package com.joohnq.muscle_management.ui.training.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingOverviewScreen(
    onNavigateToAddTraining: () -> Unit,
    onEditTraining: (String) -> Unit,
    viewModel: TrainingOverviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onEvent(event: TrainingOverviewContract.Event) {
        when (event) {
            TrainingOverviewContract.Event.AddTraining -> onNavigateToAddTraining()
            is TrainingOverviewContract.Event.AddExercise -> TODO()
            is TrainingOverviewContract.Event.EditTraining ->
                onEditTraining(event.id)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(TrainingOverviewContract.Intent.GetAll)
    }

    TrainingOverviewContent(
        state = state,
        onEvent = ::onEvent,
        onIntent = viewModel::onIntent
    )
}