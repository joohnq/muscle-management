package com.joohnq.muscle_management.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnhancedTrainingTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    errorText: String? = null,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    minLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            placeholder = { Text(placeholder) },
            singleLine = singleLine,
            minLines = minLines,
            leadingIcon = leadingIcon,
            isError = !errorText.isNullOrBlank(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            shape = MaterialTheme.shapes.medium
        )

        if (!errorText.isNullOrBlank()) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}