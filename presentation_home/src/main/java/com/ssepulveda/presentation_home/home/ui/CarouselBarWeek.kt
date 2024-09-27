package com.ssepulveda.presentation_home.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssepulveda.presentation_home.home.ui.homeContainer.HomeModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselBarWeek(homeModel: HomeModel?) {
    val size = homeModel?.weeklyReport?.size ?: 0
    val colorOnIndicator = MaterialTheme.colorScheme.secondary
    val currentWeek = homeModel?.currentWeek ?: 0
    val pagerState = rememberPagerState(
        initialPage = currentWeek,
        pageCount = {
            size
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        ) {

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            HorizontalPager(
                modifier = Modifier,
                pageSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 8.dp),
                state = pagerState
            ) { index ->
                BarWeek(homeModel?.weeklyReport?.get(index))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 1..size) {
                IndicatorItem(
                    Modifier.padding(horizontal = 4.dp),
                    size = 8.dp,
                    color = if (pagerState.currentPage + 1 == i) colorOnIndicator else Color.Gray
                )
            }
        }
    }

  /*  LaunchedEffect(key1 = Unit, block = {
        var sizeList = homeModel?.weeklyReport?.size ?: 0
        var initPage = Int.MAX_VALUE / 2
        while (initPage % sizeList != 0) {
            initPage++
        }
        pagerState.scrollToPage(initPage)
    })*/
}

@Composable
private fun IndicatorItem(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}