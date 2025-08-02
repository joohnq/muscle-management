package com.joohnq.muscle_management.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.joohnq.muscle_management.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override suspend fun getUserId(): String? {
        return withContext(Dispatchers.IO) {
            auth.currentUser?.uid
        }
    }

    override suspend fun signIn(email: String, password: String) {
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun signUp(email: String, password: String) {
        withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun signOut() {
        withContext(Dispatchers.IO) {
            auth.signOut()
        }
    }
}