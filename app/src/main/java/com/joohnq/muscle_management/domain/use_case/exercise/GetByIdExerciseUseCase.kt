package com.joohnq.muscle_management.domain.use_case.exercise

import com.joohnq.muscle_management.domain.entity.domain.Exercise
import com.joohnq.muscle_management.domain.repository.ExerciseRepository

class GetByIdExerciseUseCase(
    val repository: ExerciseRepository
) {
    suspend operator fun invoke(id: Int): Result<Exercise> {
        return try {
            val result = repository.getById(id.toString())
                ?: throw Exception("Exercise not found")

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}