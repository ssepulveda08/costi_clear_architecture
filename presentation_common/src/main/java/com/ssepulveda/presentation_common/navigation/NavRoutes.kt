package com.ssepulveda.presentation_common.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ssepulveda.presentation_common.inputs.DetailInput
import com.ssepulveda.presentation_common.inputs.MonthInput

private const val ROUTE_SPLASH = "splash"
private const val ROUTE_WELCOME = "welcome"
private const val ROUTE_HOME = "home"
private const val ROUTE_MONTHS = "months"
private const val ROUTE_BILL = "bill"
private const val ROUTE_BILL_ADD = "bill/add"
private const val ROUTE_MONTH_DETAIL= "month/%s"
private const val ROUTE_BILLS = "bills"
private const val ROUTE_BILLS_FOR_MONTH = "bills/%s"
private const val ROUTE_BILL_DETAIL = "bill/%s"
private const val ROUTE_WEB_VIEW = "webView/%s"
private const val ARG_MONTH_ID = "monthId"
private const val ARG_BILL_ID = "billID"
private const val ARG_URL = "url"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    data object Splash : NavRoutes(ROUTE_SPLASH)

    data object Home : NavRoutes(ROUTE_HOME)

    data object Welcome : NavRoutes(ROUTE_WELCOME)
    data object Months : NavRoutes(ROUTE_MONTHS)

    data object Bill : NavRoutes(ROUTE_BILL)

    data object Bill_Add : NavRoutes(ROUTE_BILL_ADD)

    data object DetailBill : NavRoutes(
        route = String.format(ROUTE_BILL_DETAIL, "{$ARG_BILL_ID}"),
        arguments = listOf(navArgument(ARG_BILL_ID) {
            type = NavType.LongType
        })
    ) {

        fun routeForDetail(input: DetailInput) = String.format(ROUTE_BILL_DETAIL, input.id)

        fun fromEntry(entry: NavBackStackEntry): DetailInput {
            return DetailInput(entry.arguments?.getLong(ARG_BILL_ID) ?: 0L)
        }
    }

    data object Bills : NavRoutes(
        route = String.format(ROUTE_BILLS_FOR_MONTH, "{${ARG_MONTH_ID}"),
        arguments = listOf(navArgument(ARG_MONTH_ID) {
            type = NavType.LongType
        })
    ) {

        fun routeForPost(monthInput: MonthInput) = String.format(ROUTE_BILLS_FOR_MONTH, monthInput.monthId)

        fun fromEntry(entry: NavBackStackEntry): MonthInput {
            return MonthInput(entry.arguments?.getLong(ARG_MONTH_ID) ?: 0L)
        }
    }

    data object DetailMonth : NavRoutes(
        route = String.format(ROUTE_MONTH_DETAIL, "{$ARG_MONTH_ID}"),
        arguments = listOf(navArgument(ARG_MONTH_ID) {
            type = NavType.LongType
        })
    ) {

        fun routeForMonth(month: Int) = String.format(ROUTE_MONTH_DETAIL, month)

        fun fromEntry(entry: NavBackStackEntry): MonthInput {
            return MonthInput(entry.arguments?.getLong(ARG_MONTH_ID) ?: 0L)
        }
    }

    data object WebView : NavRoutes(
        route = String.format(ROUTE_WEB_VIEW, "{$ARG_URL}"),
        arguments = listOf(navArgument(ARG_URL) {
            type = NavType.StringType
        })
    ) {
        fun routeForUrl(url: String) = String.format(ROUTE_WEB_VIEW, url)

        fun fromEntry(entry: NavBackStackEntry): String {
            return getUrl(entry.arguments?.getString(ARG_URL))
        }

        private fun getUrl(key: String?): String = when (key) {
            "GITHUB" -> "https://github.com/ssepulveda08/costi_clear_architecture"
            "LINKEDIN" -> "http://www.linkedin.com/in/santiagosepulvedadev"
            else -> ""
        }

    }

}