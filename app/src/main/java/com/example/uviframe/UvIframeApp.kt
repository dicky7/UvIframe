package com.example.uviframe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uviframe.ui.ConsoleLogScreen
import com.example.uviframe.ui.MainScreen
import com.example.uviframe.ui.Screen
import com.example.uviframe.ui.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UvIframeApp(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Splash.route){
                val coroutineScope = rememberCoroutineScope()
                SplashScreen(
                    navigateToMain = {
                        navController.navigate(Screen.Main.route) // Navigate to main activity
                    },
                )
            }
            composable(Screen.Main.route){
                MainScreen(
                    navigateToDetail = {
                        navController.navigate(Screen.LogView.createRoute(it))
                    }
                )
            }
            composable(
                route = Screen.LogView.route,
                arguments = listOf(navArgument("textLog"){type = NavType.StringType}),
            ){
                val text = it.arguments?.getString("textLog") ?: ""
                ConsoleLogScreen(
                    textLog = text,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    context = LocalContext.current
                )
            }
        }
    }
}