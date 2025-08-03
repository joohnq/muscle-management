package com.joohnq.muscle_management.domain.exception

sealed class TrainingException(message: String) : Exception(message) {
    data object EmptyTrainingName : TrainingException("O nome do treino é obrigatório")
    data class InvalidExerciseName(val id: String) : TrainingException("O nome do exercício é obrigatório")
    data class InvalidExerciseImage(val id: String) : TrainingException("A imagem do exercício é inválida")
}