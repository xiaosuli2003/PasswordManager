package com.xiaosuli.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.xiaosuli.passwordmanager.entity.ScreenEntity
import com.xiaosuli.passwordmanager.model.AppDatabase
import com.xiaosuli.passwordmanager.model.DataStoreRepository
import com.xiaosuli.passwordmanager.ui.screen.Add
import com.xiaosuli.passwordmanager.ui.screen.App
import com.xiaosuli.passwordmanager.ui.screen.CategoryItems
import com.xiaosuli.passwordmanager.ui.screen.Search
import com.xiaosuli.passwordmanager.ui.theme.PasswordManagerTheme
import com.xiaosuli.passwordmanager.viewmodel.AppViewModel
import com.xiaosuli.passwordmanager.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val appViewModel: AppViewModel = ViewModelProvider(
            this@MainActivity,
            AppViewModelFactory(DataStoreRepository(), AppDatabase.getDB())
        )[AppViewModel::class.java]

        setContent {
            PasswordManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val screenController = rememberNavController()
                    NavHost(
                        navController = screenController,
                        startDestination = ScreenEntity.Main.route,
                    ) {
                        composable(ScreenEntity.Main.route) {
                            App(
                                appViewModel = appViewModel,
                                searchOnClick = {
                                    screenNavigate(screenController, ScreenEntity.Search.route)
                                },
                                addOnClick = {
                                    screenNavigate(screenController, ScreenEntity.Add.route)
                                },
                                onCategoryCardClick = {
                                    screenNavigate(
                                        screenController,
                                        "${ScreenEntity.CategoryItems.route}/${it}"
                                    )
                                }
                            )
                        }
                        composable(ScreenEntity.Add.route) {
                            Add(
                                appViewModel = appViewModel,
                                onBackClick = {
                                    screenNavigate(screenController, ScreenEntity.Main.route)
                                },
                            )
                        }
                        composable(ScreenEntity.Search.route) {
                            Search(
                                appViewModel = appViewModel,
                                onBackClick = {
                                    screenNavigate(screenController, ScreenEntity.Main.route)
                                },
                            )
                        }
                        composable("${ScreenEntity.CategoryItems.route}/{category}",
                            arguments = listOf(
                                navArgument("category"){
                                    type = NavType.StringType
                                    defaultValue = "default"
                                }
                            )
                        ) {
                            CategoryItems(
                                appViewModel = appViewModel,
                                /*TODO: 此处使用了!!，去掉了空安全*/
                                category = it.arguments?.getString("category")!!,
                                onBackClick = {
                                    screenNavigate(screenController, ScreenEntity.Main.route)
                                },
                            )
                        }
                    }
                }
            }
        }
    }

    private fun screenNavigate(screenController: NavHostController, route: String) {
        screenController.navigate(route) {
            popUpTo(screenController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}