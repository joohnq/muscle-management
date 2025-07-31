package com.joohnq.muscle_management.ui.training.overview

import androidx.lifecycle.viewModelScope
import com.joohnq.muscle_management.domain.use_case.training.DeleteTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.GetAllTrainingUseCase
import com.joohnq.muscle_management.ui.BaseViewModel
import kotlinx.coroutines.launch

class TrainingOverviewViewModel(
    private val getAllTrainingUseCase: GetAllTrainingUseCase,
    private val deleteTrainingUseCase: DeleteTrainingUseCase,
    initialState: TrainingOverviewContract.State = TrainingOverviewContract.State()
) : BaseViewModel<TrainingOverviewContract.State, TrainingOverviewContract.Intent, TrainingOverviewContract.SideEffect>(
    initialState = initialState
) {

    override fun onIntent(intent: TrainingOverviewContract.Intent) {
        when (intent) {
            TrainingOverviewContract.Intent.GetAll -> getAll()
            is TrainingOverviewContract.Intent.Delete -> delete(intent.id)
        }
    }

    private fun getAll() {
        viewModelScope.launch {
            try {
                updateState { it.copy(isLoading = true) }

                val trainings = getAllTrainingUseCase().getOrThrow()

                updateState {
                    it.copy(trainings = trainings)
                }
            } catch (e: Exception) {
                updateState {
                    it.copy(isError = e)
                }
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            try {
                deleteTrainingUseCase(id)

                updateState {
                    it.copy(
                        trainings = it.trainings.filter { training -> training.first.id != id }
                    )
                }
            } catch (e: Exception) {
                emitEffect(TrainingOverviewContract.SideEffect.ShowError(e))
            }
        }
    }
}