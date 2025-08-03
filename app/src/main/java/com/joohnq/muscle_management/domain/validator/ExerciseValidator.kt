package com.joohnq.muscle_management.domain.validator

import android.util.Patterns
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.exception.TrainingException

object ExerciseValidator {
    fun validate(exercise: Exercise): List<Exception> {
        val errors = mutableListOf<Exception>()

        if (exercise.name == "")
            errors.add(TrainingException.InvalidExerciseName(exercise.id))

        if (exercise.image.isNotBlank()) {
            if (
                (!exercise.image.startsWith("http://") ||
                        !exercise.image.startsWith("https://"))
                && !Patterns.WEB_URL.matcher(exercise.image).matches()
            ) {
                errors.add(TrainingException.InvalidExerciseImage(exercise.id))
            }
        }

        return errors
    }
}