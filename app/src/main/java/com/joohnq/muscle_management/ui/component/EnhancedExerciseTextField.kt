package com.joohnq.muscle_management.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnhancedExerciseTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditing: Boolean,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    minLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    if (isEditing) {
        EnhancedTrainingTextField(
            label = label,
            placeholder = placeholder,
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            singleLine = singleLine,
            minLines = minLines,
            leadingIcon = leadingIcon
        )
    } else {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value.ifEmpty { "-" },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}