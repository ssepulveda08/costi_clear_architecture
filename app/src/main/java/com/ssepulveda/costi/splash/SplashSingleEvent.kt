package com.ssepulveda.costi.splash

import com.ssepulveda.presentation_common.state.UiSingleEvent

sealed class SplashSingleEvent : UiSingleEvent {

    data class OpenWelcomeScreen(val navRoute: String) : SplashSingleEvent()

    data class OpenHomeScreen(val navRoute: String) : SplashSingleEvent()
}