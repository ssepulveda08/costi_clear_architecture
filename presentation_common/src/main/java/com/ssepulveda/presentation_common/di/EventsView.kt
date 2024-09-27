package com.ssepulveda.presentation_common.di

import com.ssepulveda.presentation_common.state.UIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class EventsView @Inject constructor() {
    private val _uiEventFlow: MutableStateFlow<UIEvent?> = MutableStateFlow(null)
    val uiEvent = _uiEventFlow.asStateFlow()

    suspend fun submitEventFlow(uIEvent: UIEvent) {
        _uiEventFlow.emit(uIEvent)
    }

    suspend fun hideEventFlow() {
        _uiEventFlow.emit(null)
    }
}
