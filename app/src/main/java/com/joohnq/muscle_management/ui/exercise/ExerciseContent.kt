package com.joohnq.muscle_management.ui.exercise

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joohnq.muscle_management.ui.component.AddFloatingActionButton
import com.joohnq.muscle_management.ui.training.TrainingContract

@Composable
fun ExerciseContent(
    padding: PaddingValues,
    onEvent: (ExerciseContract.Event) -> Unit
) {
    Scaffold(
        modifier = Modifier.padding(padding),
        floatingActionButton = {
            AddFloatingActionButton(
                onClick = { onEvent(ExerciseContract.Event.AddTraining) }
            )
        }
    ) { _ ->

    }
}