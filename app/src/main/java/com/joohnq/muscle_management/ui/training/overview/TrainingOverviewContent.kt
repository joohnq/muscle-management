package com.joohnq.muscle_management.ui.training.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.ui.component.AddFloatingActionButton
import com.joohnq.muscle_management.ui.component.TrainingOverviewCard
import com.joohnq.muscle_management.ui.component.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingOverviewContent(
    padding: PaddingValues,
    state: TrainingOverviewContract.State,
    onEvent: (TrainingOverviewContract.Event) -> Unit = {},
    onIntent: (TrainingOverviewContract.Intent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.padding(padding),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meus Treinos",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            AddFloatingActionButton(
                onClick = { onEvent(TrainingOverviewContract.Event.AddTraining) },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> LoadingView()
                state.isError != null -> ErrorView(
                    error = state.isError,
                    onRetry = { onEvent(TrainingOverviewContract.Event.Retry) })

                state.trainings.isEmpty() && !state.isLoading -> EmptyView(onAddTraining = {
                    onEvent(
                        TrainingOverviewContract.Event.AddTraining
                    )
                })

                else -> SuccessView(
                    trainings = state.trainings,
                    onTrainingClick = { training ->
                        onEvent(
                            TrainingOverviewContract.Event.EditTraining(
                                training.first.id
                            )
                        )
                    },
                    onAddExercise = { trainingId ->
                        onEvent(
                            TrainingOverviewContract.Event.AddExercise(
                                trainingId
                            )
                        )
                    },
                    onDelete = { id ->
                        onIntent(
                            TrainingOverviewContract.Intent.Delete(id)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SuccessView(
    trainings: List<Pair<Training, List<Exercise>>>,
    onTrainingClick: (Pair<Training, List<Exercise>>) -> Unit,
    onAddExercise: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            items = trainings,
            key = { it.first.id }
        ) { training ->
            TrainingOverviewCard(
                training = training,
                onClick = { onTrainingClick(training) },
                onDelete = { onDelete(training.first.id) },
                onAddExercise = { onAddExercise(training.first.id) }
            )
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Text(
                text = "Carregando treinos...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ErrorView(
    error: Throwable?,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Erro",
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.error
        )
        VerticalSpacer(16.dp)
        Text(
            text = "Ocorreu um erro",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        VerticalSpacer(8.dp)
        Text(
            text = error?.localizedMessage ?: "Não foi possível carregar os treinos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(24.dp)
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Text("Tentar novamente")
        }
    }
}

@Composable
private fun EmptyView(onAddTraining: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Adicionar treino",
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        VerticalSpacer(16.dp)
        Text(
            text = "Nenhum treino encontrado",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        VerticalSpacer(8.dp)
        Text(
            text = "Comece criando seu primeiro treino",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(24.dp)
        Button(
            onClick = onAddTraining,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Criar primeiro treino")
        }
    }
}

@Preview
@Composable
fun TrainingContentPreview() {
    TrainingOverviewContent(
        padding = PaddingValues(),
        state = TrainingOverviewContract.State(
            trainings = listOf(
                Pair(
                    Training(), listOf(Exercise(), Exercise())
                )
            )
        ),
    )
}

@Preview
@Composable
fun TrainingContentEmptyPreview() {
    TrainingOverviewContent(
        padding = PaddingValues(),
        state = TrainingOverviewContract.State(
            trainings = listOf()
        ),
    )
}