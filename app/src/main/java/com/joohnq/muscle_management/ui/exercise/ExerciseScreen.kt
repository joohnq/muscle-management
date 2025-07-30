package com.joohnq.muscle_management.ui.exercise

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
fun ExerciseScreen(
    padding: PaddingValues,
    onNavigateToAddExercise: () -> Unit
) {
    fun onEvent(event: ExerciseContract.Event) {
        when (event) {
            ExerciseContract.Event.AddTraining -> onNavigateToAddExercise()
        }
    }

    ExerciseContent(
        padding = padding,
        onEvent = ::onEvent
    )
}