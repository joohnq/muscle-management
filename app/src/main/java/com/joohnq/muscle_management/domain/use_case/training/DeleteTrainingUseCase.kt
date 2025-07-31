package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.repository.TrainingRepository

class DeleteTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return try {
            repository.delete(id)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}