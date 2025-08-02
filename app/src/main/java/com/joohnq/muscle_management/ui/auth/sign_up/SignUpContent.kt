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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.ui.component.AppAuthPasswordTextField
import com.joohnq.muscle_management.ui.component.AppAuthTextField
import com.joohnq.muscle_management.ui.component.AuthButton
import com.joohnq.muscle_management.ui.component.AuthHeader
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
                AuthHeader()
                VerticalSpacer(16.dp)
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

                        AppAuthTextField(
                            field = state.email,
                            onValueChange = { onIntent(SignUpContract.Intent.UpdateEmail(it)) },
                        )

                        AppAuthPasswordTextField(
                            field = state.password,
                            isPasswordVisible = state.isPasswordVisible,
                            onValueChange = {
                                onIntent(SignUpContract.Intent.UpdatePassword(it))
                            },
                            onPasswordVisibilityToggle = {
                                onIntent(
                                    SignUpContract.Intent.UpdateIsPasswordVisible(
                                        !state.isPasswordVisible
                                    )
                                )
                            }
                        )

                        AuthButton(
                            isLoading = state.isLoading,
                            onClick = {
                                onIntent(SignUpContract.Intent.SignUp)
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Já tem uma conta?")
                    TextButton(onClick = { onEvent(SignUpContract.Event.NavigateSignIn) }) {
                        Text("Faça login")
                    }
                }
            }
        }
    }
}