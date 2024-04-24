package com.ssepulveda.presentation_welcome.welcome

import com.ssepulveda.presentation_common.state.UiAction

sealed class WelcomeUiAction  : UiAction {

    data object LoadData : WelcomeUiAction()
}