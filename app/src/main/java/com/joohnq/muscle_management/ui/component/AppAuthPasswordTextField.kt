package com.joohnq.muscle_management.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.joohnq.muscle_management.R
import com.joohnq.muscle_management.domain.entity.Field

@Composable
fun AppAuthPasswordTextField(
    field: Field,
    isPasswordVisible: Boolean,
    onValueChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit
) {
    OutlinedTextField(
        value = field.value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Senha") },
        placeholder = { Text("Digite sua senha") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onPasswordVisibilityToggle,
            ) {
                Icon(
                    painter = painterResource(if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                    contentDescription = if (isPasswordVisible) "Ocultar senha" else "Mostrar senha"
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
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