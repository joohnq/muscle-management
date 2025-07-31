package com.joohnq.muscle_management.domain.repository

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

interface TrainingRepository {
    suspend fun getAll(): List<Training>
    suspend fun getById(id: String): Training?
    suspend fun add(training: Training, exercises: List<Exercise>)
    suspend fun update(training: Training)
    suspend fun delete(id: String)
}