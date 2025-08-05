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
                EditTrainingContract.Intent.ChangeExerciseImage(id, image)
            is TrainingContract.Intent.UpdateExerciseName ->
                EditTrainingContract.Intent.ChangeExerciseName(id, name)
            is TrainingContract.Intent.UpdateExerciseObservations ->
                EditTrainingContract.Intent.ChangeExerciseObservations(id, observations)
            is TrainingContract.Intent.UpdateTrainingDescription ->
                EditTrainingContract.Intent.ChangeTrainingDescription(description)
            is TrainingContract.Intent.UpdateTrainingName ->
                EditTrainingContract.Intent.ChangeTrainingName(name)
        }
}