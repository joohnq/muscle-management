package com.joohnq.muscle_management.ui.training.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.ui.component.EnhancedExerciseItem
import com.joohnq.muscle_management.ui.component.EnhancedTrainingTextField
import com.joohnq.muscle_management.ui.component.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTrainingContent(
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    state: EditTrainingContract.State,
    onEvent: (EditTrainingContract.Event) -> Unit = {},
    onIntent: (EditTrainingContract.Intent) -> Unit = {}
) {
    when {
        state.isLoading -> LoadingView()
        state.isError != null -> ErrorView(
            error = state.isError,
            onRetry = { onIntent(EditTrainingContract.Intent.GetTraining(state.training.id)) }
        )

        else -> SuccessView(
            snackBarState = snackBarState,
            state = state,
            onIntent = onIntent,
            onEvent = onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessView(
    snackBarState: SnackbarHostState,
    state: EditTrainingContract.State,
    onIntent: (EditTrainingContract.Intent) -> Unit,
    onEvent: (EditTrainingContract.Event) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Editar Treino",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(EditTrainingContract.Event.OnGoBack) },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onIntent(EditTrainingContract.Intent.UpdateTraining) },
                icon = {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Salvar",
                        modifier = Modifier.size(24.dp)
                    )
                },
                text = { Text("Atualizar Treino") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Informações do Treino",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )

                            EnhancedTrainingTextField(
                                label = "Nome do Treino",
                                placeholder = "Ex: Treino de Peito",
                                value = state.training.name,
                                errorText = state.trainingNameError,
                                onValueChange = {
                                    onIntent(
                                        EditTrainingContract.Intent.UpdateTrainingName(
                                            it
                                        )
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            )

                            EnhancedTrainingTextField(
                                label = "Descrição",
                                placeholder = "Ex: 3 séries de supino...",
                                value = state.training.description,
                                errorText = state.trainingDescriptionError,
                                onValueChange = {
                                    onIntent(
                                        EditTrainingContract.Intent.UpdateTrainingDescription(
                                            it
                                        )
                                    )
                                },
                                singleLine = false,
                                minLines = 3
                            )
                        }
                    }

                    if (state.exercises.isNotEmpty()) {
                        Text(
                            text = "Exercícios (${state.exercises.size})",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            items(
                items = state.exercises,
                key = { it.id }
            ) { exercise ->
                EnhancedExerciseItem(
                    exercise = exercise,
                    isEditing = state.editingExerciseId == exercise.id,
                    onDelete = { onIntent(EditTrainingContract.Intent.DeleteExercise(exercise.id)) },
                    onEditToggle = {
                        onIntent(
                            EditTrainingContract.Intent.ToggleExerciseEdit(
                                exercise.id
                            )
                        )
                    },
                    onNameChange = { newName ->
                        onIntent(
                            EditTrainingContract.Intent.UpdateExerciseName(
                                id = exercise.id,
                                name = newName
                            )
                        )
                    },
                    onImageChange = { newImage ->
                        onIntent(
                            EditTrainingContract.Intent.UpdateExerciseImage(
                                id = exercise.id,
                                image = newImage
                            )
                        )
                    },
                    onObservationsChange = { newObservations ->
                        onIntent(
                            EditTrainingContract.Intent.UpdateExerciseObservations(
                                id = exercise.id,
                                observations = newObservations
                            )
                        )
                    },
                )
            }

            item {
                OutlinedButton(
                    onClick = { onIntent(EditTrainingContract.Intent.AddExercise) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Adicionar Exercício",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
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
            text = error?.localizedMessage ?: "Não foi possível carregar o treino",
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
                text = "Carregando treino...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    EditTrainingContent(
        state = EditTrainingContract.State(),
        onEvent = {}
    )
}