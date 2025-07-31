package com.joohnq.muscle_management.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.domain.entity.Exercise

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