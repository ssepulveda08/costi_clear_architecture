package com.ssepulveda.presentation_home.home

import com.ssepulveda.presentation_common.state.UiAction

sealed class HomeUiAction : UiAction {
    data object Load: HomeUiAction()
}