package com.joohnq.muscle_management.domain.use_case.training

import android.util.Patterns
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.exception.TrainingException
import com.joohnq.muscle_management.domain.repository.TrainingRepository

class UpdateTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(training: Training, exercises: List<Exercise>): Result<Unit> {
        return try {
            if (training.name == "")
                error(TrainingException.EmptyTrainingName)

            exercises.forEach { exercise ->
                if (exercise.name == "")
                    error(TrainingException.InvalidExerciseName(exercise.id))

                if (exercise.image.isNotBlank()) {
                    if (
                        (!exercise.image.startsWith("http://") ||
                                !exercise.image.startsWith("https://"))
                        && !Patterns.WEB_URL.matcher(exercise.image).matches()
                    ) {
                        error(TrainingException.InvalidExerciseImage(exercise.id))
                    }
                }
            }

            repository.update(training, exercises)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}