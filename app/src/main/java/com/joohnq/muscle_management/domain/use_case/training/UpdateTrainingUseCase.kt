package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.entity.domain.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository

class UpdateTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(training: Training): Result<Unit> {
        return try {
            repository.update(training)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}