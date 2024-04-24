package com.ssepulveda.presentation_welcome.welcome

import com.ssepulveda.presentation_common.state.UiSingleEvent

sealed class WelcomeSingleEvent : UiSingleEvent {

    data class OpenHomeScreen(val navRoute: String) : WelcomeSingleEvent()
}