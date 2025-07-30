package com.joohnq.muscle_management.domain.entity

import androidx.compose.ui.graphics.vector.ImageVector
import com.joohnq.muscle_management.ui.navigation.Destination

data class TopLevelRoute(
    val name: String,
    val route: Destination,
    val icon: ImageVector
)