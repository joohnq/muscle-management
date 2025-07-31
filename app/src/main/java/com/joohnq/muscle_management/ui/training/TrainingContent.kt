package com.joohnq.muscle_management.ui.training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.ui.component.AddFloatingActionButton
import com.joohnq.muscle_management.ui.component.VerticalSpacer

@Composable
fun TrainingContent(
    padding: PaddingValues,
    state: TrainingContract.State,
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
        if (state.group.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Empty",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Empty",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                state.group.forEachIndexed { index, item ->
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                            ) {
                                Text(
                                    text = item.keys.first().name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                VerticalSpacer(5.dp)
                                Text(
                                    text = item.keys.first().description,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                VerticalSpacer(20.dp)
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = MaterialTheme.shapes.small,
                                    onClick = {},
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add exercise",
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Text("Add Exercise")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TrainingContentPreview() {
    TrainingContent(
        padding = PaddingValues(),
        state = TrainingContract.State(
            group = listOf(
                mapOf(
                    Training(
                        id = "1",
                        name = "Name",
                        description = "Description",
                    ) to listOf(
                        Exercise(
                            id = "1",
                            name = "Name",
                            image = "Image",
                            observations = "Observations",
                            trainingId = "1"
                        )
                    )
                )
            )
        ),
        onEvent = {}
    )
}

@Preview
@Composable
fun TrainingContentEmptyPreview() {
    TrainingContent(
        padding = PaddingValues(),
        state = TrainingContract.State(
            group = listOf()
        ),
        onEvent = {}
    )
}