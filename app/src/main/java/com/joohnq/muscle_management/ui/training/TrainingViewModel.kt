package com.joohnq.muscle_management.ui.training

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrainingViewModel: ViewModel() {
    private val _state: MutableStateFlow<TrainingContract.State> = MutableStateFlow(TrainingContract.State())
    val state: StateFlow<TrainingContract.State> = _state.asStateFlow()


    fun onIntent(intent: TrainingContract.Intent) {

    }
}