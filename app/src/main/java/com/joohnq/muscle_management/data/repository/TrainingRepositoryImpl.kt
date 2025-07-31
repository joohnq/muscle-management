package com.joohnq.muscle_management.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TrainingRepositoryImpl(
    private val firestore: FirebaseFirestore
) : TrainingRepository {
    private val collection = firestore.collection(TRAINING_COLLECTION_NAME)

    override suspend fun getAll(): List<Training> {
        val snapshot = collection.get().await()
        return snapshot.toObjects(Training::class.java)
    }

    override suspend fun getById(id: String): Training? {
        val document = collection.document(id).get().await()
        return document.toObject(Training::class.java)
    }

    override suspend fun add(training: Training, exercises: List<Exercise>) {
        withContext(Dispatchers.IO) {
            val trainingRef = collection.document()
            val trainingId = trainingRef.id

            val batch = firestore.batch()

            batch.set(trainingRef, training.copy(id = trainingId))

            exercises.forEach { exercise ->
                val exerciseRef = trainingRef.collection(TRAINING_EXERCISES_COLLECTION_NAME).document()
                batch.set(exerciseRef, exercise.copy(
                    id = exerciseRef.id,
                    trainingId = trainingId,
                ))
            }

            batch.commit().await()
        }
    }

    override suspend fun update(training: Training) {
        collection.document(training.id).set(training).await()
    }

    override suspend fun delete(id: String) {
        collection.document(id).delete().await()
    }

    companion object {
        const val TRAINING_COLLECTION_NAME = "trainings"
        const val TRAINING_EXERCISES_COLLECTION_NAME = "exercises"
    }
}