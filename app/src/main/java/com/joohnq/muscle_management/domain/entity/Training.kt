package com.joohnq.muscle_management.domain.entity

import com.google.firebase.Timestamp
import java.util.UUID

data class Training(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val date: Timestamp = Timestamp.now(),
)