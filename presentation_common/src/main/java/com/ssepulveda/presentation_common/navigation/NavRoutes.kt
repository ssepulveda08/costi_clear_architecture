package com.ssepulveda.presentation_common.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

private const val ROUTE_SPLASH = "splash"
private const val ROUTE_WELCOME = "welcome"
private const val ROUTE_HOME = "home"
private const val ROUTE_BILL = "bill"
private const val ROUTE_BILL_ADD = "bill/add"
private const val ROUTE_BILLS = "bills"
private const val ROUTE_BILLS_FOR_MONTH = "bills/%s"
private const val ARG_MONTH_ID = "monthId"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    data object Splash : NavRoutes(ROUTE_SPLASH)

    data object Home : NavRoutes(ROUTE_HOME)

    data object Welcome : NavRoutes(ROUTE_WELCOME)

    data object Bill : NavRoutes(ROUTE_BILL)

    data object Bill_Add : NavRoutes(ROUTE_BILL_ADD)

    data object Bills : NavRoutes(
        route = String.format(ROUTE_BILLS_FOR_MONTH, "{$ARG_MONTH_ID}"),
        arguments = listOf(navArgument(ARG_MONTH_ID) {
            type = NavType.LongType
        })
    ) {

        fun routeForPost(monthInput: MonthInput) = String.format(ROUTE_BILLS, monthInput.monthId)

        fun fromEntry(entry: NavBackStackEntry): MonthInput {
            return MonthInput(entry.arguments?.getLong(ARG_MONTH_ID) ?: 0L)
        }
    }

}