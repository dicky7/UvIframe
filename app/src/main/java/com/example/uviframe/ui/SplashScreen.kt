package com.example.uviframe.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.uviframe.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToMain: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(1500) // Delay for 30 seconds
        navigateToMain() // Trigger navigation to main activity
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 8.dp)
                .size(180.dp)
                .clip(RoundedCornerShape(50)),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )
    }
}