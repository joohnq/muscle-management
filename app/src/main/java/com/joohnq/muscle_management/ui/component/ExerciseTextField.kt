package com.joohnq.muscle_management.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

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