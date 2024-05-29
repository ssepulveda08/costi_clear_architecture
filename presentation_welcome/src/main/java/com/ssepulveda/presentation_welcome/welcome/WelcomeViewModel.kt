package com.ssepulveda.presentation_welcome.welcome

import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.data.getDefaultData
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.SaveCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.SaveInitialConfigurationByDefaultUseCase
import com.ssepulveda.costi.domain.useCase.UpdateInitialConfigurationUseCase
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
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
        viewModelScope.launch {
            saveInitialConfigurationByDefaultUseCase.execute(
                SaveInitialConfigurationByDefaultUseCase.Request(
                    getDefaultData()
                )
            ).collect { result ->
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