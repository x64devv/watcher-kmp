package com.x64dev.watcher

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "watcher-kmp",
    ) {
        App()
    }
}