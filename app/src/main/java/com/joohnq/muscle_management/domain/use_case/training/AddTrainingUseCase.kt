package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository

class AddTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(training: Training): Result<Unit> {
        return try {
            repository.add(training)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}