package com.joohnq.muscle_management.domain.use_case.auth

import com.joohnq.muscle_management.domain.repository.AuthRepository

class GetUserIdUseCase(
    val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<String?> {
        return try {
            val id = repository.getUserId()

            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}