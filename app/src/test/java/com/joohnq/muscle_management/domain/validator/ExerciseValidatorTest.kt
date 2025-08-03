package com.joohnq.muscle_management.domain.validator

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.exception.TrainingException
import com.joohnq.muscle_management.domain.validator.ExerciseValidator
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ExerciseValidatorTest {

    @Test
    fun `validate should return error when exercise name is empty`() {
        val exercise = Exercise(id = "e1", name = "", trainingId = "t1", image = "")
        val errors = ExerciseValidator.validate(exercise)

        assertEquals(1, errors.size)
        assertTrue(errors.any { it is TrainingException.InvalidExerciseName && it.id == "e1" })
    }

    @Test
    fun `validate should return error when image url is invalid`() {
        val exercise = Exercise(id = "e2", name = "Push Up", trainingId = "t1", image = "invalid_url")
        val errors = ExerciseValidator.validate(exercise)

        assertEquals(1, errors.size)
        assertTrue(errors.any { it is TrainingException.InvalidExerciseImage && it.id == "e2" })
    }

    @Test
    fun `validate should return no errors when exercise is valid`() {
        val exercise = Exercise(id = "e3", name = "Push Up", trainingId = "t1", image = "https://example.com/image.png")
        val errors = ExerciseValidator.validate(exercise)

        assertTrue(errors.isEmpty())
    }

    @Test
    fun `validate should return no errors when image is blank`() {
        val exercise = Exercise(id = "e4", name = "Sit Up", trainingId = "t1", image = "")
        val errors = ExerciseValidator.validate(exercise)

        assertTrue(errors.isEmpty())
    }
}