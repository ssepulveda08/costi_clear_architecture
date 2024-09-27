package com.ssepulveda.costi.domain.useCase.account

import android.util.Log
import com.ssepulveda.costi.domain.entity.Account
import com.ssepulveda.costi.domain.repository.AccountRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map


/*private val DEFAULT_ACCOUNT = Account(
    id = 7777,
    month = 0,
    name = "Cuenta principal",
    capped = 0.0
)*/

class GetAccountsByMonthUseCase(
    configuration: Configuration,
    private val localConfigurationRepository: LocalConfigurationRepository,
    private val accountRepository: AccountRepository,
) : UseCase<GetAccountsByMonthUseCase.Request, GetAccountsByMonthUseCase.Response>(configuration) {

    data object Request : UseCase.Request

    data class Response(val list: List<Account>) : UseCase.Response

    override fun process(request: Request): Flow<Response> =
        localConfigurationRepository.getMonthSet().flatMapConcat { idMonth ->
            accountRepository.getAccountsByMonth(idMonth).map { list ->
               // val mutableList = list.toMutableList()
                //val default = DEFAULT_ACCOUNT.copy(month = idMonth)
                /*val accounts = list.ifEmpty {
                    listOf(

                    )
                }*/
               // mutableList.add(0, default)
                Log.d("POTATO", "ACCOUNTS USE CASE $list")
                Response(list)
            }
        }
}
