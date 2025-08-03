package com.joohnq.muscle_management.domain.use_case.training

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import com.joohnq.muscle_management.domain.validator.ExerciseValidator
import com.joohnq.muscle_management.domain.validator.TrainingValidator

class AddTrainingUseCase(
    val repository: TrainingRepository,
    private val trainingValidator: TrainingValidator,
    private val exerciseValidator: ExerciseValidator,
) {
    suspend operator fun invoke(training: Training, exercises: List<Exercise>): Result<Unit> {
        return try {
            val errors = mutableListOf<Exception>()

            errors.addAll(trainingValidator.validate(training))

            exercises.forEach { exercise ->
                errors.addAll(exerciseValidator.validate(exercise))
            }

            if (errors.isNotEmpty())
                return Result.failure(ValidationException(errors))

            repository.add(training, exercises)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}