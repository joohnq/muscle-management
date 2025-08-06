package com.joohnq.muscle_management.ui.training.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joohnq.muscle_management.domain.mapper.EditTrainingContractMapper.toEditTrainingIntent
import com.joohnq.muscle_management.ui.component.TrainingSuccessView
import com.joohnq.muscle_management.ui.component.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTrainingContent(
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    state: EditTrainingContract.State,
    onEvent: (EditTrainingContract.Event) -> Unit = {},
    onIntent: (EditTrainingContract.Intent) -> Unit = {},
) {
    when {
        state.isLoading -> LoadingView()
        state.isError != null -> ErrorView(
            error = state.isError,
            onRetry = { onIntent(EditTrainingContract.Intent.GetTraining(state.trainingState.training.id)) }
        )

        else -> TrainingSuccessView(
            snackBarState = snackBarState,
            state = state.trainingState,
            title = "Editar Treino",
            actionText = "Atualizar Treino",
            onIntent = { onIntent(it.toEditTrainingIntent()) },
            onGoBack = { onEvent(EditTrainingContract.Event.OnGoBack) },
        )
    }
}

@Composable
private fun ErrorView(
    error: Throwable?,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Erro",
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.error
        )
        VerticalSpacer(16.dp)
        Text(
            text = "Ocorreu um erro",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        VerticalSpacer(8.dp)
        Text(
            text = error?.localizedMessage ?: "Não foi possível atualizar o treino",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(24.dp)
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Text("Tentar novamente")
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Text(
                text = "Atualizando treino...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}