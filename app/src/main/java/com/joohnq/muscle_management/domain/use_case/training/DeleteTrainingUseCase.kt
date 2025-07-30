package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.repository.ExerciseRepository
import com.joohnq.muscle_management.domain.repository.TrainingRepository

class DeleteTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return try {
            repository.delete(id.toString())

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}