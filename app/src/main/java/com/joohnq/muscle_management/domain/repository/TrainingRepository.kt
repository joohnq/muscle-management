package com.joohnq.muscle_management.domain.repository

import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training

interface TrainingRepository {
    suspend fun getAll(): List<Pair<Training, List<Exercise>>>
    suspend fun getById(id: String): Pair<Training, List<Exercise>>?
    suspend fun add(training: Training, exercises: List<Exercise>)
    suspend fun update(training: Training)
    suspend fun delete(id: String)
}