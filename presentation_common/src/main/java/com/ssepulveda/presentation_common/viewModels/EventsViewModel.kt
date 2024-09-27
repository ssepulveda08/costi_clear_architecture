package com.ssepulveda.presentation_common.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssepulveda.presentation_common.state.UIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsViewModel @Inject constructor(): ViewModel() {

    private val _uiEventFlow: MutableStateFlow<UIEvent?> = MutableStateFlow(null)
    val uiEvent = _uiEventFlow.asStateFlow()


    fun submitEventFlow(uIEvent: UIEvent) {
        viewModelScope.launch {
            _uiEventFlow.emit(uIEvent)
        }
    }

    fun hideEventFlow() {
        viewModelScope.launch {
            _uiEventFlow.emit(null)
        }
    }

}