package com.joohnq.muscle_management.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.muscle_management.domain.entity.domain.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import kotlinx.coroutines.tasks.await

class TrainingRepositoryImpl(
    private val firestore: FirebaseFirestore
) : TrainingRepository {
    private val collection = firestore.collection("trainings")

    override suspend fun getAll(): List<Training> {
        val snapshot = collection.get().await()
        return snapshot.toObjects(Training::class.java)
    }

    override suspend fun getById(id: String): Training? {
        val document = collection.document(id).get().await()
        return document.toObject(Training::class.java)
    }

    override suspend fun add(training: Training): String {
        val documentRef = collection.add(training).await()
        return documentRef.id
    }

    override suspend fun update(training: Training) {
        collection.document(training.id.toString()).set(training).await()
    }

    override suspend fun delete(id: String) {
        collection.document(id).delete().await()
    }
}