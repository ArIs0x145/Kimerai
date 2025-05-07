package io.github.aris0x145.kimerai.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * MVI 架構的基礎 ViewModel
 * 提供狀態管理和一次性事件的通用實現
 */
abstract class MviViewModel<I : MviIntent, S : MviState, E : MviEffect> : ViewModel() {

    private val initialState: S by lazy { createInitialState() }
    abstract fun createInitialState(): S

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>()
    val effect = _effect.receiveAsFlow()

    /**
     * 處理用戶意圖 - 所有子類需要實現此方法
     */
    abstract fun handleIntent(intent: I)

    /**
     * 更新當前狀態
     */
    protected fun updateState(reducer: S.() -> S) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    /**
     * 發送一次性效果
     */
    protected fun sendEffect(effect: E) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}