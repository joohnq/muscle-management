package com.joohnq.muscle_management.domain.validator

import com.joohnq.muscle_management.domain.exception.AuthException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EmailValidatorTest {

    @Test
    fun `validate should return error when email is empty`() {
        val errors = EmailValidator.validate("")

        assertEquals(1, errors.size)
        assertTrue(errors.contains(AuthException.EmptyEmailException))
    }

    @Test
    fun `validate should return error when email is invalid`() {
        val errors = EmailValidator.validate("invalid-email")

        assertEquals(1, errors.size)
        assertTrue(errors.any { it is AuthException.InvalidEmailException })
    }

    @Test
    fun `validate should return no errors when email is valid`() {
        val errors = EmailValidator.validate("user@example.com")

        assertTrue(errors.isEmpty())
    }
}