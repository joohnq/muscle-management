package com.joohnq.muscle_management.ui

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalViewModel<STATE, INTENT, EFFECT> {
    val state: StateFlow<STATE>

    val sideEffect: SharedFlow<EFFECT>

    fun onIntent(intent: INTENT)
}