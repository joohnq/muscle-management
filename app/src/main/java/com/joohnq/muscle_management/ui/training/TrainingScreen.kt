package com.joohnq.muscle_management.ui.training

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
fun TrainingScreen(
    padding: PaddingValues,
    onNavigateToAddTraining: () -> Unit
) {
    fun onEvent(event: TrainingContract.Event) {
        when (event) {
            TrainingContract.Event.AddTraining -> onNavigateToAddTraining()
        }
    }

    TrainingContent(
        padding = padding,
        onEvent = ::onEvent
    )
}