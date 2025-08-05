package com.joohnq.muscle_management.domain.mapper

import com.joohnq.muscle_management.ui.training.TrainingContract
import com.joohnq.muscle_management.ui.training.add.AddTrainingContract

object AddTrainingContractMapper {
    fun TrainingContract.Intent.toAddTrainingIntent(): AddTrainingContract.Intent =
        when (this) {
            TrainingContract.Intent.ActionButton ->
                AddTrainingContract.Intent.AddTraining

            TrainingContract.Intent.AddExercise ->
                AddTrainingContract.Intent.AddExercise

            is TrainingContract.Intent.DeleteExercise ->
                AddTrainingContract.Intent.DeleteExercise(id)

            is TrainingContract.Intent.ToggleExerciseEdit ->
                AddTrainingContract.Intent.ToggleExerciseEdit(id)

            is TrainingContract.Intent.UpdateExerciseImage ->
                AddTrainingContract.Intent.ChangeExerciseImage(id, image)

            is TrainingContract.Intent.UpdateExerciseName ->
                AddTrainingContract.Intent.ChangeExerciseName(id, name)

            is TrainingContract.Intent.UpdateExerciseObservations ->
                AddTrainingContract.Intent.ChangeExerciseObservations(id, observations)

            is TrainingContract.Intent.UpdateTrainingDescription ->
                AddTrainingContract.Intent.ChangeTrainingDescription(description)

            is TrainingContract.Intent.UpdateTrainingName ->
                AddTrainingContract.Intent.ChangeTrainingName(name)
        }
}