package com.joohnq.muscle_management.domain.validator

import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.exception.TrainingException
import com.joohnq.muscle_management.domain.validator.TrainingValidator
import org.junit.Assert.*
import org.junit.Test

class TrainingValidatorTest {

    @Test
    fun `validate should return error when training name is empty`() {
        val training = Training(id = "t1", name = "")
        val errors = TrainingValidator.validate(training)

        assertEquals(1, errors.size)
        assertTrue(errors.contains(TrainingException.EmptyTrainingName))
    }

    @Test
    fun `validate should return no errors when training name is not empty`() {
        val training = Training(id = "t2", name = "Push Day")
        val errors = TrainingValidator.validate(training)

        assertTrue(errors.isEmpty())
    }
}