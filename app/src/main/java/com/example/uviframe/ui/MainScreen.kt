@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uviframe.ui

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.web.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit

) {
    //default url
    var url by remember { mutableStateOf("https://devuvos.uvcr.me/sukha7629190c74c54215944f0c99d1d178cd/merchant/4618111") }
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()
    var textFieldValue by remember(state.content.getCurrentUrl()) {
        mutableStateOf(state.content.getCurrentUrl() ?: "")
    }
    var lastConsoleMessage: String by remember { mutableStateOf("") }
    var webView: WebView? = null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicTextField(
                        value = textFieldValue,
                        onValueChange = {textFieldValue = it},
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth() // Take available width
                            .width(IntrinsicSize.Min),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                url = textFieldValue
                                webView?.loadUrl(textFieldValue)
                            }
                        )

                    )
                },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { navigator.navigateBack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        IconButton(onClick = { navigator.navigateForward() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Forward"
                            )
                        }
                        TextButton(onClick = {navigateToDetail(lastConsoleMessage)}) {
                            Text("Log-view")
                        }
                    }
                }
            )
        },
        content = { it ->
            val loadingState = state.loadingState
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // A custom WebViewClient and WebChromeClient can be provided via subclassing
            val webClient = remember {
                object : AccompanistWebViewClient() {
                    override fun onPageStarted(
                        view: WebView?,
                        url: String?,
                        favicon: Bitmap?
                    ) {
                        super.onPageStarted(view, url, favicon)
                        Log.d("Accompanist WebView", "Page started loading for $url")
                    }
                }
            }

            val chromeClient = remember {
                object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        Log.e("CONSOLE MESSAGE", consoleMessage?.message().toString())
                        lastConsoleMessage = consoleMessage?.message().toString() ?: ""
                        return true
                    }
                }
            }

//            WebView(
//                state = state,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(it),
//                navigator = navigator,
//                onCreated = { webView ->
//                    webView.webChromeClient =  chromeClient
//                    webView.settings.javaScriptEnabled = true
//
//                },
//                client = webClient,
//                factory = {
//                    WebView(it).apply {
//                        webChromeClient = chromeClient
//                    }
//                }
//            )

            AndroidView(
                modifier = Modifier.padding(it),
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webChromeClient = chromeClient
                        settings.javaScriptEnabled = true
                        loadUrl(url)
                        webView = this
                    }
                }, update = {
                    webView = it
                })
        }
    )
}