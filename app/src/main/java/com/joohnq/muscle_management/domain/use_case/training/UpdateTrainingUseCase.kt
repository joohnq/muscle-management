package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository

class UpdateTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(training: Training, exercises: List<Exercise>): Result<Unit> {
        return try {
            repository.update(training, exercises)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}