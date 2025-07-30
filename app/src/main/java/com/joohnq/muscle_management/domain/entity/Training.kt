package com.joohnq.muscle_management.domain.entity

import java.sql.Timestamp

data class Training(
    val id: Int,
    val name: String,
    val description: String,
    val date: Timestamp
)