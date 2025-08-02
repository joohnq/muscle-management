package com.joohnq.muscle_management.domain.use_case.auth

import com.joohnq.muscle_management.domain.repository.AuthRepository

class SignOutUseCase(
    val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            repository.signOut()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}