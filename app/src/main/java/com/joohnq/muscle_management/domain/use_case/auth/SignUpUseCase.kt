package com.joohnq.muscle_management.domain.use_case.auth

import android.util.Patterns
import com.joohnq.muscle_management.domain.exception.AuthException
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.mapper.AuthExceptionMapper
import com.joohnq.muscle_management.domain.repository.AuthRepository

class SignUpUseCase(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val errors = mutableListOf<Exception>()

            if (email.isEmpty()) {
                errors.add(AuthException.EmptyEmailException)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errors.add(AuthException.InvalidEmailException())
            }

            if (password.isEmpty()) {
                errors.add(AuthException.EmptyPasswordException)
            }

            if (errors.isNotEmpty())
                return Result.failure(ValidationException(errors))

            repository.signUp(email, password)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(AuthExceptionMapper.map(e))
        }
    }
}