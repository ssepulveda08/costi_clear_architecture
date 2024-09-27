package com.ssepulveda.costi.domain.useCase.account

import com.ssepulveda.costi.domain.entity.Account
import com.ssepulveda.costi.domain.repository.AccountRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AddAccountUseCase(
    configuration: Configuration,
    private val localConfigurationRepository: LocalConfigurationRepository,
    private val accountRepository: AccountRepository,
) : UseCase<AddAccountUseCase.Request, AddAccountUseCase.Response>(configuration) {

    data class Request(val account: Account) : UseCase.Request

    data object Response : UseCase.Response

    override fun process(request: Request): Flow<Response> = localConfigurationRepository.getMonthSet().flatMapConcat {
            val account = request.account.copy(month = it)
            accountRepository.registerAccount(account).map {
                Response
            }
    }

}