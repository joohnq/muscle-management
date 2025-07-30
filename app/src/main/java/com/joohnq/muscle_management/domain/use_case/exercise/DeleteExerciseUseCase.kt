package com.joohnq.muscle_management.domain.use_case.exercise

import com.joohnq.muscle_management.domain.repository.ExerciseRepository

class DeleteExerciseUseCase(
    val repository: ExerciseRepository
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