package com.ssepulveda.costi.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.GetCostTypeUseCase
import com.ssepulveda.costi.domain.useCase.GetInitialConfigurationUseCase
import com.ssepulveda.costi.domain.useCase.UpdateInitialConfigurationUseCase
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCostTypeUseCase: GetCostTypeUseCase,
    private val getInitialConfiguration: GetInitialConfigurationUseCase,
    private val initConfigUseCase: GetInitialConfigurationUseCase,
    private val updateInitialConfigurationUseCase: UpdateInitialConfigurationUseCase
) : MviViewModel<Unit, UiState<Unit>, SplashUiAction, SplashSingleEvent>() {

    private fun checkConfiguration() {
        viewModelScope.launch {
            getInitialConfiguration.execute(GetInitialConfigurationUseCase.Request).collect {
               convert(it)
            }
        }
    }
    private  fun convert(result: Result<GetInitialConfigurationUseCase.Response>) {
        return when (result) {
            is Result.Error -> {
                //UiState.Error(result.exception.localizedMessage.orEmpty())
            }
            is Result.Success -> {
                //UiState.Success(result.data)
                handleConfig(result.data.isSet)
            }
        }
    }

    private fun handleConfig(isSet: Boolean) {
        if (isSet) {
            showHomeScreen()
        } else {
            showWelcomeScreen()
        }
    }

    private fun showWelcomeScreen() {
        submitSingleEvent(
            SplashSingleEvent.OpenWelcomeScreen(
                NavRoutes.Welcome.route
            )
        )
    }
    private fun showHomeScreen() {
        submitSingleEvent(
            SplashSingleEvent.OpenWelcomeScreen(
                NavRoutes.Home.route
            )
        )
    }

    override fun initState(): UiState<Unit> = UiState.Loading

    override fun handleAction(action: SplashUiAction) {
        when (action) {
            SplashUiAction.ValidateConfiguration -> checkConfiguration()
            SplashUiAction.ShowWelcomeScreen -> showWelcomeScreen()
            else -> {}
        }
    }
}
