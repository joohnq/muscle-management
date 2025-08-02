package com.joohnq.muscle_management.domain.use_case.auth

import com.joohnq.muscle_management.domain.mapper.AuthExceptionMapper
import com.joohnq.muscle_management.domain.repository.AuthRepository

class SignUpUseCase(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            repository.signUp(email, password)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(AuthExceptionMapper.map(e))
        }
    }
}