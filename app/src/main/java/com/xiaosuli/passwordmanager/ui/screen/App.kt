package com.xiaosuli.passwordmanager.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaosuli.passwordmanager.R
import com.xiaosuli.passwordmanager.entity.BottomNavEntity
import com.xiaosuli.passwordmanager.ui.components.ColorScheme
import com.xiaosuli.passwordmanager.ui.theme.PasswordManagerTheme
import com.xiaosuli.passwordmanager.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
fun App(
    appViewModel: AppViewModel,
    searchOnClick: () -> Unit,
    addOnClick: () -> Unit,
    onCategoryCardClick:(String)->Unit
) {
    val items = listOf(
        BottomNavEntity.Home,
        BottomNavEntity.ColorScheme,
        BottomNavEntity.Settings,
    )
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    when (pagerState.currentPage) {
                        0 -> {
                            IconButton(onClick = searchOnClick) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            IconButton(onClick = addOnClick) {
                                Icon(
                                    imageVector = Icons.Rounded.AddCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar{
                items.forEachIndexed { index, nav ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = nav.icon,
                                contentDescription = null,
                                modifier = Modifier
                            )
                        },
                        label = {
                            Text(
                                text = nav.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                modifier = Modifier,
                            )
                        },
                        modifier = Modifier,
                        alwaysShowLabel = false,
                        selected = index == pagerState.currentPage,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            pageCount = 3,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> Home(
                    appViewModel = appViewModel,
                    onCategoryCardClick = onCategoryCardClick
                    )

                1 -> ColorScheme()
                2 -> Settings()
            }
        }
        BackHandler(pagerState.currentPage != 0) {
            coroutineScope.launch {
                pagerState.scrollToPage(0)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PasswordManagerTheme {
    }
}