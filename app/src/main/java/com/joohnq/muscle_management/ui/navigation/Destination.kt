package com.joohnq.muscle_management.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    data object Training: Destination

    @Serializable
    data object Exercise: Destination

    @Serializable
    data object Splash: Destination
}