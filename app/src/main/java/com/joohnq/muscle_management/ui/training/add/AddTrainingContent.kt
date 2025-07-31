package com.joohnq.muscle_management.ui.training.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.R
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.ui.component.EnhancedExerciseItem
import com.joohnq.muscle_management.ui.component.EnhancedTrainingTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrainingContent(
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    state: AddTrainingContract.State,
    onEvent: (AddTrainingContract.Event) -> Unit = {},
    onIntent: (AddTrainingContract.Intent) -> Unit = {}
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Criar Treino",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(AddTrainingContract.Event.OnGoBack) },
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
                onClick = { onIntent(AddTrainingContract.Intent.AddTraining) },
                icon = {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Salvar",
                        modifier = Modifier.size(24.dp)
                    )
                },
                text = { Text("Salvar Treino") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        LazyColumn(
            state = scrollState,
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
                                onValueChange = { onIntent(AddTrainingContract.Intent.UpdateTrainingName(it)) },
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
                                    onIntent(AddTrainingContract.Intent.UpdateTrainingDescription(it))
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
                    onDelete = { onIntent(AddTrainingContract.Intent.DeleteExercise(exercise.id)) },
                    onEditToggle = { onIntent(AddTrainingContract.Intent.ToggleExerciseEdit(exercise.id)) },
                    onNameChange = { newName ->
                        onIntent(AddTrainingContract.Intent.UpdateExerciseName(
                            id = exercise.id,
                            name = newName
                        ))
                    },
                    onImageChange = { newImage ->
                        onIntent(AddTrainingContract.Intent.UpdateExerciseImage(
                            id = exercise.id,
                            image = newImage
                        ))
                    },
                    onObservationsChange = { newObservations ->
                        onIntent(AddTrainingContract.Intent.UpdateExerciseObservations(
                            id = exercise.id,
                            observations = newObservations
                        ))
                    },
                )
            }

            item {
                OutlinedButton(
                    onClick = { onIntent(AddTrainingContract.Intent.AddExercise) },
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

@Preview
@Composable
private fun Preview() {
    AddTrainingContent(
        state = AddTrainingContract.State(),
        onEvent = {}
    )
}

@Preview
@Composable
private fun Preview2() {
    AddTrainingContent(
        state = AddTrainingContract.State(
            trainingNameError = "Some error",
            trainingDescriptionError = "Some error",
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun Preview3() {
    AddTrainingContent(
        state = AddTrainingContract.State(
            exercises = listOf(
                Exercise(
                    name = "Name",
                    image = "Image",
                    observations = "Observations"
                )
            )
        ),
        onEvent = {}
    )
}