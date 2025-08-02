package com.joohnq.muscle_management.ui.auth.sign_up

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    navigateNext: () -> Unit,
    navigateSignIn: () -> Unit,
    viewModel: SignUpViewModel = koinViewModel()
) {
    val snackBarState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onEvent(event: SignUpContract.Event) {
        when (event) {
            SignUpContract.Event.NavigateSignIn -> navigateSignIn()
            SignUpContract.Event.CloseKeyboard -> focusManager.clearFocus()
        }
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SignUpContract.SideEffect.NavigateNext -> navigateNext()
                is SignUpContract.SideEffect.ShowError -> {
                    snackBarState.showSnackbar(sideEffect.error.message.toString())
                }
            }
        }
    }

    SignUpContent(
        snackBarState = snackBarState,
        state = state,
        onIntent = viewModel::onIntent,
        onEvent = ::onEvent
    )
}