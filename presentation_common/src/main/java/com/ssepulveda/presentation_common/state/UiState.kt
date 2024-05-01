package com.ssepulveda.presentation_common.state

sealed class UiState<out T : Any> {
    data object  Default : UiState<Nothing>()
    data object  Loading : UiState<Nothing>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
    data class ShowDialog(val message: String) : UiState<Nothing>()
    data class Success<T : Any>(val data: T) : UiState<T>()
}