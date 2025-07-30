package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository

class GetByIdTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(id: Int): Result<Training> {
        return try {
            val result = repository.getById(id.toString())
                ?: throw Exception("Exercise not found")

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}