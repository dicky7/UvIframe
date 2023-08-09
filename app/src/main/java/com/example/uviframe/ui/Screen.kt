package com.example.uviframe.ui

sealed class Screen(val route: String){
    object Splash: Screen("splash")
    object Main: Screen("main")
    object LogView : Screen("log/{textLog}") {
        fun createRoute(textLog: String) = "log/$textLog"
    }
}
