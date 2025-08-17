package com.x64dev.watcher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import watcher_kmp.composeapp.generated.resources.Res
import watcher_kmp.composeapp.generated.resources.host_24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun WatcherAppBar(){
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf("api.zimtickets.com") }
    var sites  = listOf<String>("api.zimtickets.com", "test-api.zimtickets.com", "api-admin.zimtickets.com")
    TopAppBar(
        modifier = Modifier.padding(8.dp),
        title = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(resource = Res.drawable.host_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(width = 8.dp))
                Text(
                    text = "Watcher",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(16.dp).weight(1f))
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    OutlinedTextField(
                        value = selectedValue,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = TextStyle(fontSize = 12.sp),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).width(200.dp),
                        shape = RoundedCornerShape(24.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        sites.forEach { site ->
                            DropdownMenuItem(
                                text = { Text(site, color = MaterialTheme.colorScheme.onSurface) },
                                onClick = {
                                    selectedValue = site
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }

                    }

                }

            }
        }
    )
}
