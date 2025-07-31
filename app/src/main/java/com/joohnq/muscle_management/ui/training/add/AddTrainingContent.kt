package com.joohnq.muscle_management.ui.training.add

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.domain.entity.Exercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrainingContent(
    padding: PaddingValues,
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    state: AddTrainingContract.State,
    onEvent: (AddTrainingContract.Event) -> Unit = {},
    onIntent: (AddTrainingContract.Intent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.padding(padding),
        snackbarHost = { SnackbarHost(snackBarState) },
        topBar = {
            TopAppBar(
                title = { Text("Novo Treino") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(AddTrainingContract.Event.OnGoBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onIntent(AddTrainingContract.Intent.AddTraining) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Salvar")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                TrainingTextField(
                    label = "Nome",
                    placeholder = "Ex: Treino de Peito",
                    value = state.training.name,
                    errorText = state.trainingNameError,
                    onValueChange = { onIntent(AddTrainingContract.Intent.UpdateTrainingName(it)) },
                )

                Spacer(modifier = Modifier.height(16.dp))

                TrainingTextField(
                    label = "Descrição",
                    placeholder = "Ex: 3 séries de supino...",
                    value = state.training.description,
                    errorText = state.trainingDescriptionError,
                    onValueChange = {
                        onIntent(
                            AddTrainingContract.Intent.UpdateTrainingDescription(
                                it
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            if (state.exercises.isNotEmpty()) {
                item {
                    Text(
                        text = "Exercícios",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(
                    items = state.exercises,
                    key = { it.id }
                ) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        isEditing = state.editingExerciseId == exercise.id,
                        onDelete = { onIntent(AddTrainingContract.Intent.DeleteExercise(exercise.id)) },
                        onEditToggle = {
                            onIntent(AddTrainingContract.Intent.ToggleExerciseEdit(exercise.id))
                        },
                        onNameChange = { newName ->
                            onIntent(
                                AddTrainingContract.Intent.UpdateExerciseName(
                                    id = exercise.id,
                                    name = newName
                                )
                            )
                        },
                        onImageChange = { newImage ->
                            onIntent(
                                AddTrainingContract.Intent.UpdateExerciseImage(
                                    id = exercise.id,
                                    image = newImage
                                )
                            )
                        },
                        onObservationsChange = { newObservations ->
                            onIntent(
                                AddTrainingContract.Intent.UpdateExerciseObservations(
                                    id = exercise.id,
                                    observations = newObservations
                                )
                            )
                        },
                    )
                }
            }

            item {
                Button(
                    onClick = { onIntent(AddTrainingContract.Intent.AddExercise) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 10.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Adicionar Exercício")
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    onDelete: () -> Unit,
    isEditing: Boolean,
    onEditToggle: () -> Unit,
    onNameChange: (String) -> Unit,
    onImageChange: (String) -> Unit,
    onObservationsChange: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(if (isEditing) 16.dp else 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = if (isEditing) 8.dp else 0.dp
            )
        ) {
            ExerciseTextField(
                value = exercise.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Name",
                isEditing = isEditing
            )

            ExerciseTextField(
                value = exercise.image,
                onValueChange = onImageChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Image",
                isEditing = isEditing
            )

            ExerciseTextField(
                value = exercise.observations,
                onValueChange = onObservationsChange,
                singleLine = false,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Observações",
                isEditing = isEditing
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onEditToggle) {
                    Icon(
                        imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = if (isEditing) "Confirmar" else "Editar"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Remover")
                }
            }
        }
    }
}

@Composable
fun TrainingTextField(
    label: String,
    placeholder: String,
    value: String,
    errorText: String? = null,
    onValueChange: (String) -> Unit
) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        singleLine = true,
        shape = MaterialTheme.shapes.medium
    )
    if (!errorText.isNullOrBlank()) {
        Text(
            text = errorText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun ExerciseTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    isEditing: Boolean = true,
) {
    if (isEditing) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            singleLine = singleLine,
            placeholder = { Text(placeholder) },
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$placeholder: ",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AddTrainingContent(
        padding = PaddingValues(),
        state = AddTrainingContract.State(),
        onEvent = {}
    )
}

@Preview
@Composable
private fun Preview2() {
    AddTrainingContent(
        padding = PaddingValues(),
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
        padding = PaddingValues(),
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