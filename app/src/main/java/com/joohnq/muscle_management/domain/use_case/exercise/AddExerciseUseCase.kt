package com.joohnq.muscle_management.domain.use_case.exercise

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.repository.ExerciseRepository

class AddExerciseUseCase(
    val repository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: Exercise): Result<Unit> {
        return try {
            repository.add(exercise)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}