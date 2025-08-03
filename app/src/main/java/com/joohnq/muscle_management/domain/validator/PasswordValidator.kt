package com.joohnq.muscle_management.domain.validator

import com.joohnq.muscle_management.domain.exception.AuthException

object PasswordValidator {
    fun validate(password: String): List<Exception> {
        val errors = mutableListOf<Exception>()

        if (password.isEmpty()) {
            errors.add(AuthException.EmptyPasswordException)
        }

        return errors
    }
}