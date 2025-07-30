package com.joohnq.muscle_management.domain.use_case.exercise

import com.joohnq.muscle_management.domain.entity.domain.Exercise
import com.joohnq.muscle_management.domain.repository.ExerciseRepository

class GetAllExerciseUseCase(
    val repository: ExerciseRepository
) {
    suspend operator fun invoke(): Result<List<Exercise>> {
        return try {
            val result = repository.getAll()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}