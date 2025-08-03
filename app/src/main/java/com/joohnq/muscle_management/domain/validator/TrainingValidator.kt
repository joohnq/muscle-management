package com.joohnq.muscle_management.domain.validator

import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.exception.TrainingException

object TrainingValidator {
    fun validate(training: Training): List<Exception> {
        val errors = mutableListOf<Exception>()

        if (training.name.isEmpty()) {
            errors.add(TrainingException.EmptyTrainingName)
        }

        return errors
    }
}