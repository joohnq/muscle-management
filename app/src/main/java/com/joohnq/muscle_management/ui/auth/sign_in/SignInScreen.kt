package com.joohnq.muscle_management.ui.auth.sign_in

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    navigateNext: () -> Unit,
    navigateSignUp: () -> Unit,
    viewModel: SignInViewModel = koinViewModel()
) {
    val snackBarState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onEvent(event: SignInContract.Event) {
        when (event) {
            SignInContract.Event.NavigateSignUp -> navigateSignUp()
            SignInContract.Event.CloseKeyboard -> focusManager.clearFocus()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SignInContract.SideEffect.NavigateNext -> navigateNext()
                is SignInContract.SideEffect.ShowError -> {
                    snackBarState.showSnackbar(sideEffect.message)
                }
            }
        }
    }

    SignInContent(
        snackBarState = snackBarState,
        state = state,
        onIntent = viewModel::onIntent,
        onEvent = ::onEvent
    )
}