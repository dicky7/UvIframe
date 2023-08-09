package com.example.uviframe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.uviframe.ui.MainScreen
import com.example.uviframe.ui.theme.UVIFRAMETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UVIFRAMETheme {
                UvIframeApp()
            }
        }
    }
}