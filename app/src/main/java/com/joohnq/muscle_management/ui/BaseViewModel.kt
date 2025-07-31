package com.joohnq.muscle_management.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, INTENT, EFFECT>(
    private val initialState: STATE,
) : ViewModel(),
    UnidirectionalViewModel<STATE, INTENT, EFFECT> {
    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<STATE> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<EFFECT>()
    override val sideEffect: SharedFlow<EFFECT> = _sideEffect.asSharedFlow()

    private val handledOneTimeEvents = mutableSetOf<INTENT>()

    /**
     * Updates the [STATE] of the ViewModel.
     *
     * @param update A function that takes the current [STATE] and produces a new [STATE].
     */
    protected fun updateState(update: (STATE) -> STATE) {
        _state.update(update)
    }

    /**
     * Emits a side effect.
     *
     * @param effect The [EFFECT] to emit.
     */
    protected fun emitEffect(effect: EFFECT) {
        viewModelScope.launch {
            _sideEffect.emit(effect)
        }
    }

    protected fun resetState() {
        updateState { initialState }
    }
}