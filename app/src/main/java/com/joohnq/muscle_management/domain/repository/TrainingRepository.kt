package com.joohnq.muscle_management.domain.repository

import com.joohnq.muscle_management.domain.entity.Training

interface TrainingRepository {
    suspend fun getAll(): List<Training>
    suspend fun getById(id: String): Training?
    suspend fun add(training: Training): String
    suspend fun update(training: Training)
    suspend fun delete(id: String)
}