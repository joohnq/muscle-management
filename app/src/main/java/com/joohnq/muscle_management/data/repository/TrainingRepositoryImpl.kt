package com.joohnq.muscle_management.data.repository

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

    override suspend fun getAll(): List<Pair<Training, List<Exercise>>> =
        withContext(Dispatchers.IO) {
            val trainings = collection.get().await().toObjects(Training::class.java)

            trainings.map { training ->
                val exercises = collection
                    .document(training.id)
                    .collection(TRAINING_EXERCISES_COLLECTION_NAME)
                    .get()
                    .await()
                    .toObjects(Exercise::class.java)

                training to exercises
            }
        }

    override suspend fun getById(id: String): Pair<Training, List<Exercise>>? =
        withContext(Dispatchers.IO) {
            val trainingSnapshot = collection.document(id).get().await()
            val training =
                trainingSnapshot.toObject(Training::class.java) ?: return@withContext null

            val exercisesSnapshot = collection.document(id)
                .collection(TRAINING_EXERCISES_COLLECTION_NAME)
                .get()
                .await()

            val exercises = exercisesSnapshot.toObjects(Exercise::class.java)

            training to exercises
        }

    override suspend fun add(training: Training, exercises: List<Exercise>) {
        withContext(Dispatchers.IO) {
            val trainingRef = collection.document()
            val trainingId = trainingRef.id

            val batch = firestore.batch()

            batch.set(trainingRef, training.copy(id = trainingId))

            exercises.forEach { exercise ->
                val exerciseRef =
                    trainingRef.collection(TRAINING_EXERCISES_COLLECTION_NAME).document()
                batch.set(
                    exerciseRef, exercise.copy(
                        id = exerciseRef.id,
                        trainingId = trainingId,
                    )
                )
            }

            batch.commit().await()
        }
    }

    override suspend fun update(training: Training, exercises: List<Exercise>) {
        collection.document(training.id).set(training).await()

        val existingExercises = collection.document(training.id)
            .collection(TRAINING_EXERCISES_COLLECTION_NAME)
            .get()
            .await()

        val batch = firestore.batch()
        existingExercises.forEach { batch.delete(it.reference) }

        exercises.forEach { exercise ->
            val docRef = collection.document(training.id)
                .collection(TRAINING_EXERCISES_COLLECTION_NAME)
                .document(exercise.id)

            batch.set(docRef, exercise.copy(
                id = docRef.id,
                trainingId = training.id
            ))
        }

        batch.commit().await()
    }

    override suspend fun delete(id: String) {
        collection.document(id).delete().await()
    }

    companion object {
        const val TRAINING_COLLECTION_NAME = "trainings"
        const val TRAINING_EXERCISES_COLLECTION_NAME = "exercises"
    }
}