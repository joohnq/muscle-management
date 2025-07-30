package com.joohnq.muscle_management.ui.training

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joohnq.muscle_management.ui.component.AddFloatingActionButton

@Composable
fun TrainingContent(
    padding: PaddingValues,
    onEvent: (TrainingContract.Event) -> Unit
) {
    Scaffold(
        modifier = Modifier.padding(padding),
        floatingActionButton = {
            AddFloatingActionButton(
                onClick = { onEvent(TrainingContract.Event.AddTraining) }
            )
        }
    ) { _ ->

    }
}