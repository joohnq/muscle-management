package com.joohnq.muscle_management.ui.training.overview

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.use_case.auth.SignOutUseCase
import com.joohnq.muscle_management.domain.use_case.training.DeleteTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.GetAllTrainingUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class TrainingOverviewViewModel(
    private val getAllTrainingUseCase: GetAllTrainingUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val deleteTrainingUseCase: DeleteTrainingUseCase,
    initialState: TrainingOverviewContract.State = TrainingOverviewContract.State()
) : BaseViewModel<TrainingOverviewContract.State, TrainingOverviewContract.Intent, TrainingOverviewContract.SideEffect>(
    initialState = initialState
) {

    override fun onIntent(intent: TrainingOverviewContract.Intent) {
        when (intent) {
            TrainingOverviewContract.Intent.GetAll -> getAll()
            TrainingOverviewContract.Intent.SignOut -> signOut()
            is TrainingOverviewContract.Intent.Delete -> delete(intent.id)
        }
    }

    private fun getAll() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val trainings = getAllTrainingUseCase()
                .getOrElse { error ->
                    updateState { it.copy(isError = error) }
                    return@launch
                }

            updateState {
                it.copy(trainings = trainings)
            }

            updateState { it.copy(isLoading = false) }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
                .getOrElse { error ->
                    emitEffect(TrainingOverviewContract.SideEffect.ShowError(error))
                }

            emitEffect(TrainingOverviewContract.SideEffect.NavigateToSignIn)
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            deleteTrainingUseCase(id)
                .getOrElse {error ->
                    emitEffect(TrainingOverviewContract.SideEffect.ShowError(error))
                }

            updateState {
                it.copy(
                    trainings = it.trainings.filter { training -> training.first.id != id }
                )
            }
        }
    }
}