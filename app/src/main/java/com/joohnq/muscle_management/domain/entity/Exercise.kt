package com.joohnq.muscle_management.domain.entity

import java.util.UUID

data class Exercise(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val image: String = "",
    val observations: String = "",
    val trainingId: String? = null,
    val orderId: Int = 0
)