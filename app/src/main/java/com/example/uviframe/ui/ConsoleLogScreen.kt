@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uviframe.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConsoleLogScreen(
    textLog: String,
    navigateBack: () -> Unit,
    context: Context
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Console Log")
                },
                navigationIcon = {
                    IconButton(onClick = {navigateBack()}) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.padding(it),
                    onClick = {
                        copyToClipboard(context, textLog)
                    }
                ) {
                    MaterialTheme.typography.bodyMedium
                    Text("Copy to Clipboard")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = textLog,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
                )
            }

        }
    )
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Logcat Output", text)
    clipboard.setPrimaryClip(clip)
}