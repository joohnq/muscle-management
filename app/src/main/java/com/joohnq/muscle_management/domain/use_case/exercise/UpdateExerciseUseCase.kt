package com.joohnq.muscle_management.domain.use_case.exercise

import com.joohnq.muscle_management.domain.entity.domain.Exercise
import com.joohnq.muscle_management.domain.repository.ExerciseRepository

class UpdateExerciseUseCase(
    val repository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: Exercise): Result<Unit> {
        return try {
            repository.update(exercise)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}