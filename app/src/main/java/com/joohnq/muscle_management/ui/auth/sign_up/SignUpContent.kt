package com.joohnq.muscle_management.ui.auth.sign_up

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.R
import com.joohnq.muscle_management.ui.component.VerticalSpacer

@Composable
fun SignUpContent(
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    state: SignUpContract.State,
    onEvent: (SignUpContract.Event) -> Unit = {},
    onIntent: (SignUpContract.Intent) -> Unit = {}
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarState) },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onEvent(SignUpContract.Event.CloseKeyboard)
                        }
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                VerticalSpacer(16.dp)
                Text(
                    text = "Muscle Management",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Controle seus treinos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Cadastrar-se",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        OutlinedTextField(
                            value = state.email,
                            onValueChange = { onIntent(SignUpContract.Intent.UpdateEmail(it)) },
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
                            isError = state.emailError != null,
                            supportingText = {
                                if (state.emailError != null) {
                                    Text(
                                        text = state.emailError,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = state.password,
                            onValueChange = { onIntent(SignUpContract.Intent.UpdatePassword(it)) },
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
                                    onClick = {
                                        onIntent(
                                            SignUpContract.Intent.UpdateIsPasswordVisible(
                                                !state.isPasswordVisible
                                            )
                                        )
                                    },
                                ) {
                                    Icon(
                                        painter = painterResource(if (state.isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                                        contentDescription = if (state.isPasswordVisible) "Ocultar senha" else "Mostrar senha"
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            visualTransformation = if (state.isPasswordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            isError = state.passwordError != null,
                            supportingText = {
                                if (state.passwordError != null) {
                                    Text(
                                        text = state.passwordError,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            singleLine = true
                        )

                        Button(
                            onClick = { onIntent(SignUpContract.Intent.SignUp) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = !state.isLoading,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Cadastrar-se",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("J´q tem uma conta?")
                    TextButton(onClick = { onEvent(SignUpContract.Event.NavigateSignIn) }) {
                        Text("Faça login")
                    }
                }
            }
        }
    }
}