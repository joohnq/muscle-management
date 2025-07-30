package com.joohnq.muscle_management.domain.repository

import com.joohnq.muscle_management.domain.entity.Exercise

interface ExerciseRepository {
    suspend fun getAll(): List<Exercise>
    suspend fun getById(id: String): Exercise?
    suspend fun add(exercise: Exercise): String
    suspend fun update(exercise: Exercise)
    suspend fun delete(id: String)
}