package com.joohnq.muscle_management.domain.validator

import android.util.Patterns
import com.joohnq.muscle_management.domain.exception.AuthException

object EmailValidator {
    fun validate(email: String): List<Exception> {
        val errors = mutableListOf<Exception>()

        if (email.isEmpty()) {
            errors.add(AuthException.EmptyEmailException)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.add(AuthException.InvalidEmailException())
        }

        return errors
    }
}