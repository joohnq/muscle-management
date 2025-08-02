package com.joohnq.muscle_management.domain.repository

interface AuthRepository {
    suspend fun getUserId(): String?
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
}