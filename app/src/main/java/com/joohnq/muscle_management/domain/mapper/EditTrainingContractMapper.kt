package com.joohnq.muscle_management.domain.mapper

import com.joohnq.muscle_management.ui.training.TrainingContract
import com.joohnq.muscle_management.ui.training.edit.EditTrainingContract

object EditTrainingContractMapper {
    fun TrainingContract.Intent.toEditTrainingIntent(): EditTrainingContract.Intent =
        when (this) {
            TrainingContract.Intent.ActionButton ->
                EditTrainingContract.Intent.UpdateTraining
            TrainingContract.Intent.AddExercise ->
                EditTrainingContract.Intent.AddExercise
            is TrainingContract.Intent.DeleteExercise ->
                EditTrainingContract.Intent.DeleteExercise(id)
            is TrainingContract.Intent.ToggleExerciseEdit ->
                EditTrainingContract.Intent.ToggleExerciseEdit(id)
            is TrainingContract.Intent.UpdateExerciseImage ->
                EditTrainingContract.Intent.UpdateExerciseImage(id, image)
            is TrainingContract.Intent.UpdateExerciseName ->
                EditTrainingContract.Intent.UpdateExerciseName(id, name)
            is TrainingContract.Intent.UpdateExerciseObservations ->
                EditTrainingContract.Intent.UpdateExerciseObservations(id, observations)
            is TrainingContract.Intent.UpdateTrainingDescription ->
                EditTrainingContract.Intent.UpdateTrainingDescription(description)
            is TrainingContract.Intent.UpdateTrainingName ->
                EditTrainingContract.Intent.UpdateTrainingName(name)
        }
}