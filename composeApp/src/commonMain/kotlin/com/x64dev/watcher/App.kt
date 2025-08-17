package com.x64dev.watcher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.x64dev.watcher.ui.Navigation

@Composable
fun App() {
    Watcher()
}

@Composable
fun Watcher(){
    MaterialTheme {
        Box {
            Navigation()
        }
    }
}

