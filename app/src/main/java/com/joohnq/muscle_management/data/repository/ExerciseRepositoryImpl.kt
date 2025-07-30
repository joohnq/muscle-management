package com.joohnq.muscle_management.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.muscle_management.domain.entity.domain.Exercise
import com.joohnq.muscle_management.domain.repository.ExerciseRepository
import kotlinx.coroutines.tasks.await

class ExerciseRepositoryImpl(
    private val firestore: FirebaseFirestore
): ExerciseRepository {
    private val collection = firestore.collection("exercises")

    override suspend fun getAll(): List<Exercise> {
        val snapshot = collection.get().await()
        return snapshot.toObjects(Exercise::class.java)
    }

    override suspend fun getById(id: String): Exercise? {
        val document = collection.document(id).get().await()
        return document.toObject(Exercise::class.java)
    }

    override suspend fun add(exercise: Exercise): String {
        val documentRef = collection.add(exercise).await()
        return documentRef.id
    }

    override suspend fun update(exercise: Exercise) {
        collection.document(exercise.id.toString()).set(exercise).await()
    }

    override suspend fun delete(id: String) {
        collection.document(id).delete().await()
    }
}