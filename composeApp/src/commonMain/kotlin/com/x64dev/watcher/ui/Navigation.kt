package com.x64dev.watcher.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.x64dev.watcher.ui.dashboard.Dashboard
import com.x64dev.watcher.ui.dashboard.LogsTerminal
import com.x64dev.watcher.ui.dashboard.Settings

var navItems = listOf<String>("Dashboard", "Logs", "Settings")
var navIcons = listOf<ImageVector>(Icons.Outlined.Dashboard, Icons.Outlined.Terminal, Icons.Outlined.Settings)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    var selectedItem by remember { mutableStateOf(1) }
    Box(modifier = Modifier) {
        Column {
            WatcherAppBar()
            Box(modifier = Modifier.weight(1f)){
                when (selectedItem) {
                    0 -> Dashboard()
                    1 -> LogsTerminal()
                    2 -> Settings()
                }
            }
            NavigationBar(
                modifier = Modifier.border(width = 0.3.dp, color = Color.Gray.copy(alpha = 0.1f) ).shadow(elevation = 5.dp),
                tonalElevation = 5.dp,
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedItem,
                        onClick = {
                            selectedItem = index
                        },
                        icon = {
                            Icon(imageVector = navIcons[index], contentDescription = null)
                        },
                        modifier = Modifier,
                        enabled = true,
                        label = {
                            Text(text = item)
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(),
                    )
                }
            }
        }

    }
}