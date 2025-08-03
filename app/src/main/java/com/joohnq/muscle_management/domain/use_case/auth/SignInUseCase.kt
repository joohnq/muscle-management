package com.joohnq.muscle_management.domain.use_case.auth

import android.util.Patterns
import com.joohnq.muscle_management.domain.exception.AuthException
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.mapper.AuthExceptionMapper
import com.joohnq.muscle_management.domain.repository.AuthRepository
import com.joohnq.muscle_management.domain.validator.EmailValidator
import com.joohnq.muscle_management.domain.validator.PasswordValidator

class SignInUseCase(
    val repository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val errors = mutableListOf<Exception>()

            errors.addAll(emailValidator.validate(email))
            errors.addAll(passwordValidator.validate(password))

            if (errors.isNotEmpty())
                return Result.failure(ValidationException(errors))

            repository.signIn(email, password)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(AuthExceptionMapper.map(e))
        }
    }
}