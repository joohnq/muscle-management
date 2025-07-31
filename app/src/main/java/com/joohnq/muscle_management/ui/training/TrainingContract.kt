package com.joohnq.muscle_management.ui.training

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

sealed interface TrainingContract {
    sealed interface Event{
        data object AddTraining: Event
    }

    sealed interface Intent

    data class State(
        val group: List<Map<Training, List<Exercise>>> = listOf()
    )
}