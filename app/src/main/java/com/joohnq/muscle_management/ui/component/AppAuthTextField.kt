package com.joohnq.muscle_management.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.joohnq.muscle_management.domain.entity.Field

@Composable
fun AppAuthTextField(
    field: Field,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = field.value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Email") },
        placeholder = { Text("exemplo@email.com") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = field.error != null,
        supportingText = {
            if (field.error != null) {
                Text(
                    text = field.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        singleLine = true
    )
}