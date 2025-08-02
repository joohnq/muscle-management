package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository

class GetAllTrainingUseCase(
    val repository: TrainingRepository
) {
    suspend operator fun invoke(): Result<List<Pair<Training, List<Exercise>>>> {
        return try {
            val result = repository.getAll()

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}