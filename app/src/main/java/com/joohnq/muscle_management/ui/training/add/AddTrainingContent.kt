package com.joohnq.muscle_management.ui.training.add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.joohnq.muscle_management.domain.mapper.AddTrainingContractMapper.toAddTrainingIntent
import com.joohnq.muscle_management.ui.component.TrainingSuccessView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrainingContent(
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    state: AddTrainingContract.State,
    onEvent: (AddTrainingContract.Event) -> Unit = {},
    onIntent: (AddTrainingContract.Intent) -> Unit = {},
) {
    TrainingSuccessView(
        snackBarState = snackBarState,
        title = "Cadastrar Treino",
        actionText = "Adicionar Treino",
        state = state.trainingState,
        onIntent = { onIntent(it.toAddTrainingIntent()) },
        onGoBack = { onEvent(AddTrainingContract.Event.OnGoBack) },
    )
}