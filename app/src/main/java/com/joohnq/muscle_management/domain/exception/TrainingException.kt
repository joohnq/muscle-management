package com.joohnq.muscle_management.domain.exception

sealed class TrainingException : Exception() {
    data object EmptyTrainingName : TrainingException()
    data class InvalidExerciseName(val id: String) : TrainingException()
    data class InvalidExerciseImage(val id: String) : TrainingException()
}