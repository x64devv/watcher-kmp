package com.x64dev.watcher.ui.terminal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Expand
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

var dateFormatter  = LocalDateTime.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
    char(' ')
    hour()
    char(':')
    minute()
    char(':')
    second()
};
var logFilters = listOf<String>("All", "Error", "Warning", "Info", "Debug")

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ContextSheet(){
    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetState = rememberModalBottomSheetState()
    if(showBottomSheet){
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            },
        ) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    showBottomSheet = false
                }
            ) {
                Text("Close")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Terminal(){
    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)){
        Column {
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Laravel Logs",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "Live",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp,
                )
                Icon(imageVector = Icons.Outlined.LiveTv, contentDescription = null, tint = Color.Green, modifier = Modifier.size(12.dp))
            }
            Box(modifier = Modifier.padding(8.dp).background(color = Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))){
                Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically){
                    var text by remember { mutableStateOf("")}
                    Icon(modifier = Modifier.padding(end = 8.dp),imageVector = Icons.Outlined.Search, contentDescription = null)
                    TextField(
                        value = text,
                        onValueChange = { e ->
                            text = e
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 12.sp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(24.dp)
                    )
                }
            }
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())){
                logFilters.forEach { filter ->
                    Box(modifier = Modifier.padding(end = 8.dp).background(color = Color.Gray.copy(alpha = .4f), shape = RoundedCornerShape(16.dp)).clickable {} ){
                        Text(modifier = Modifier.padding(8.dp), text = filter.toUpperCase(Locale.current))
                    }
                }
            }
            var mylist = remember { mutableStateListOf("") }

            LaunchedEffect(Unit) {
                for(i in 0..5){
                    mylist.add(i.toString())
                    println("added an item")
                    delay(3000L)
                }
            }

            Box(modifier = Modifier.padding(top = 16.dp).fillMaxSize().background(color = Color.White, shape = RoundedCornerShape(16.dp))){
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    mylist.forEachIndexed { index, item ->
                        LogCard(
                            item = item,
                            index = index
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LogCard(
    item: String,
    index: Int
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetState = rememberModalBottomSheetState()

    Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth().height(200.dp).background(color = Color.Gray.copy(alpha = .05f), shape = RoundedCornerShape(16.dp))){
        Column(modifier = Modifier) {
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Outlined.RadioButtonChecked, contentDescription = null, tint = Color.Gray.copy(alpha = 0.4f), modifier = Modifier.size(16.dp))
                Box(modifier = Modifier.padding(start = 8.dp).background( color = Color.Gray.copy(alpha = .3f), shape = RoundedCornerShape(16.dp))){
                    Text(text = "Info".toUpperCase(Locale.current), fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = dateFormatter.format(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Text(text = "User authentication successful", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Icon(modifier = Modifier.clickable(enabled = true, onClick = {
                    showBottomSheet = true
                }),
                    imageVector = Icons.Outlined.ExpandMore,
                    contentDescription = null,
                )
                Text(text = "{user : jake}", fontSize = 18.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp), color = Color.Black.copy(alpha = 0.5f))
            }
        }
    }

    // Modal Bottom Sheet - moved outside the Box to avoid layout issues
    if(showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Details for item: $item", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                CodeBlock(code = "{user : jake}", language = "Logs")
                Spacer(modifier = Modifier.height(16.dp))
                Button (onClick = {
                    showBottomSheet = false
                }){
                    Text("Close")
                }
            }
        }
    }
}


@Composable
fun CodeBlock(
    code: String,
    modifier: Modifier = Modifier,
    language: String? = null,
    showCopyButton: Boolean = true,
    backgroundColor: Color = Color(0xFF1E1E1E), // Dark background
    textColor: Color = Color(0xFFD4D4D4), // Light gray text
    cornerRadius: Dp = 8.dp
) {
    val clipboardManager = LocalClipboardManager.current
    var showCopiedMessage by remember { mutableStateOf(false) }

    LaunchedEffect(showCopiedMessage) {
        if (showCopiedMessage) {
            delay(2000) // Show "Copied!" message for 2 seconds
            showCopiedMessage = false
        }
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Column {
            // Header with language label and copy button
            if (language != null || showCopyButton) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Language label
                    if (language != null) {
                        Text(
                            text = language.uppercase(),
                            fontSize = 12.sp,
                            color = Color(0xFF888888),
                            fontWeight = FontWeight.Medium
                        )
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    // Copy button
                    if (showCopyButton) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    clipboardManager.setText(AnnotatedString(code))
                                    showCopiedMessage = true
                                }
                                .padding(4.dp)
                        ) {
                            if (showCopiedMessage) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = "Copied",
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Copied!",
                                    fontSize = 12.sp,
                                    color = Color(0xFF4CAF50),
                                    fontWeight = FontWeight.Medium
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.ContentCopy,
                                    contentDescription = "Copy code",
                                    tint = Color(0xFF888888),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Copy",
                                    fontSize = 12.sp,
                                    color = Color(0xFF888888)
                                )
                            }
                        }
                    }
                }

                // Divider
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFF333333)
                )
            }

            // Code content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                items(code.lines().size) { line ->
                    var lineText = code.lines()[line]
                    Text(
                        text = lineText.ifEmpty { " " }, // Preserve empty lines
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        color = textColor,
                        lineHeight = 20.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleCodeBlock(
    code: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFF5F5F5), // Light background
    textColor: Color = Color(0xFF333333), // Dark text
    cornerRadius: Dp = 4.dp
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(12.dp)
    ) {
        Text(
            text = code,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            color = textColor,
            lineHeight = 20.sp
        )
    }
}


