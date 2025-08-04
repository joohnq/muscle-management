package com.joohnq.muscle_management.ui.training

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

interface TrainingContract {
    data class State(
        val training: Training = Training(),
        val exercises: List<Exercise> = listOf(),
        val isLoading: Boolean = false,
        val trainingNameError: String? = null,
        val trainingDescriptionError: String? = null,
        val editingExerciseId: String? = null,
        val isError: Throwable? = null,
        val editingExerciseNameError: String? = null,
        val editingExerciseImageError: String? = null,
        val editingExerciseErrorId: String? = null,
    )

    sealed interface Intent {
        data class UpdateTrainingName(val name: String) : Intent
        data class UpdateTrainingDescription(val description: String) : Intent
        data class UpdateExerciseName(val id: String, val name: String) : Intent
        data class UpdateExerciseImage(val id: String, val image: String) :
            Intent

        data class UpdateExerciseObservations(val id: String, val observations: String) :
            Intent

        data class DeleteExercise(val id: String) : Intent
        data class ToggleExerciseEdit(val id: String) : Intent
        data object AddExercise : Intent
        data object ActionButton : Intent
    }
}