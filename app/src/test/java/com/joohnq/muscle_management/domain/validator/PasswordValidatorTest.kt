package com.joohnq.muscle_management.domain.validator

import com.joohnq.muscle_management.domain.exception.AuthException
import com.joohnq.muscle_management.domain.validator.PasswordValidator
import org.junit.Assert.*
import org.junit.Test

class PasswordValidatorTest {

    @Test
    fun `validate should return error when password is empty`() {
        val errors = PasswordValidator.validate("")

        assertEquals(1, errors.size)
        assertTrue(errors.contains(AuthException.EmptyPasswordException))
    }

    @Test
    fun `validate should return no errors when password is not empty`() {
        val errors = PasswordValidator.validate("mysecret123")

        assertTrue(errors.isEmpty())
    }
}