package com.ssepulveda.presentation_welcome.welcome

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.data.getDefaultData
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.config.SaveCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.config.SaveInitialConfigurationByDefaultUseCase
import com.ssepulveda.costi.domain.useCase.config.UpdateInitialConfigurationUseCase
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val saveInitialConfigurationByDefaultUseCase: SaveInitialConfigurationByDefaultUseCase,
    private val updateInitialConfigurationUseCase: UpdateInitialConfigurationUseCase,
    private val saveCurrentMonthUseCase: SaveCurrentMonthUseCase,
) : MviViewModel<Unit, UiState<Unit>, WelcomeUiAction, WelcomeSingleEvent>() {

    private fun loadDate() {
        Log.d("POTATO", "WELCOME loadDate")
        viewModelScope.launch {
            val result = saveInitialConfigurationByDefaultUseCase.execute(
                SaveInitialConfigurationByDefaultUseCase.Request(
                    getDefaultData()
                )
            ).take(1).first()
            when (result) {
                is Result.Error -> {
                    UiState.Error(result.exception.localizedMessage.orEmpty())
                }

                is Result.Success -> {
                    saveConfig()
                }
            }
        }
    }


    private fun showWelcomeScreen() {
        submitSingleEvent(
            WelcomeSingleEvent.OpenHomeScreen(
                NavRoutes.Home.route
            )
        )
    }

    private fun saveConfig() {
        viewModelScope.launch {
            val combined = updateInitialConfigurationUseCase.execute(
                UpdateInitialConfigurationUseCase.Request(true)
            ).combine(
                saveCurrentMonthUseCase.execute(SaveCurrentMonthUseCase.Request(getCurrentMonth()))
            ) { a, b ->
                "$a - $b"
            }
            combined.collect {
                delay(1500)
                showWelcomeScreen()
            }
        }
    }

    private fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    override fun initState(): UiState<Unit> = UiState.Default

    override fun handleAction(action: WelcomeUiAction) {
        when (action) {
            WelcomeUiAction.LoadData -> {
                submitState(UiState.Loading)
                loadDate()
            }
        }
    }

}