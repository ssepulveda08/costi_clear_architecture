package com.ssepulveda.costi.splash

import com.ssepulveda.presentation_common.state.UiAction

sealed class SplashUiAction : UiAction {

    data object ValidateConfiguration : SplashUiAction()
    data object ShowWelcomeScreen : SplashUiAction()
    data object loadData : SplashUiAction()
}
