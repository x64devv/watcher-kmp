package com.x64dev.watcher.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x64dev.watcher.ui.terminal.Terminal
import io.github.dautovicharis.charts.PieChart
import io.github.dautovicharis.charts.model.toChartDataSet
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.random.Random

var logger = KotlinLogging.logger { }

var tabs = listOf<String>("Nginx", "Laravel")
var devices = mapOf<String, ImageVector>(
    "Desktop" to Icons.Outlined.DesktopWindows,
    "Mobile" to Icons.Outlined.PhoneAndroid,
    "Tablet" to Icons.Outlined.Tablet
)
var operatingSystems = mapOf<String, ImageVector>(
    "Windows" to Icons.Outlined.LaptopWindows,
    "MacOs" to Icons.Outlined.LaptopMac,
    "Android" to Icons.Outlined.Android,
    "Other" to Icons.Outlined.DevicesOther
)
var browsers = mapOf<String, ImageVector>(
    "Chrome" to Icons.Outlined.LaptopChromebook,
    "Firefox" to Icons.Outlined.Fireplace,
    "Other" to Icons.Outlined.DevicesOther
)

@Composable
fun Dashboard() {

    var selectedTab by remember { mutableStateOf(0) }
    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceContainer).fillMaxSize()) {
        Column(modifier = Modifier) {
            TabRow(modifier = Modifier, selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = (index == selectedTab),
                        modifier = Modifier,
                        onClick = {
                            selectedTab = index
                        },
                        text = {
                            Text(text = tab)
                        },
                    )
                }
            }

            Box(modifier = Modifier) {
                when (selectedTab) {
                    0 -> NginxTab()
                    1 -> LaravelTab()
                }
            }
        }

    }
}

@Composable
private fun AddDefaultPieChart() {
    val dataSet = listOf(8.0f, 23.0f, 54.0f, 32.0f, 12.0f, 37.0f, 7.0f, 23.0f, 43.0f)
        .toChartDataSet(
            title = "Pie Title",
            postfix = " °C"
        )
    PieChart(dataSet)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NginxTab() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        //Date filter
        Row(modifier = Modifier) {
            Spacer(modifier = Modifier.weight(1f))
            DateFilter()
        }
        // Requests and Visitors Cards
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            CountStatCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .height(200.dp)
                    .width(150.dp)
                    .weight(1f),
                statName = "Total Requests",
                statValue = 879,
                statPercentage = 12,
            )
            CountStatCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .height(200.dp)
                    .width(150.dp)
                    .weight(1f),
                statName = "Unique Visitors",
                statValue = 554,
                statPercentage = 1,
            )
        }
        // Popular Devices
        StatsDistribution(
            stat = "Popular Devices",
            values = mapOf<String, Int>("Desktop" to 100, "Mobile" to 230, "Tablet" to 12),
            icons = devices
        )
        StatsDistribution(
            stat = "Operating Systems",
            values = mapOf<String, Int>("Windows" to 150, "MacOs" to 130, "Android" to 112),
            icons = operatingSystems
        )
        StatsDistribution(
            stat = "Browsers",
            values = mapOf<String, Int>("Chrome" to 150, "Firefox" to 130, "Other" to 112),
            icons = browsers
        )

    }
}

@Composable
fun CountStatCard(modifier: Modifier = Modifier, statName: String, statValue: Int, statPercentage: Int) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = statName, fontSize = 16.sp, color = Color.Black.copy(alpha = .7f))
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Outlined.KeyboardArrowUp, contentDescription = null)
            }
            Text(
                modifier = Modifier.weight(2f),
                text = "${statValue}",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 38.sp,
                color = Color.Black
            )
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "${statPercentage}%", fontSize = 16.sp, color = Color.Black.copy(alpha = .7f))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Outlined.ArrowDropUp, contentDescription = null)
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFilter() {
    var selectedDateFilter by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var dateFilters = listOf<String>("Last 24Hrs", "Last 7 Days", "Last Month")
    ExposedDropdownMenuBox(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = dateFilters[selectedDateFilter],
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(fontSize = 12.sp),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.DateRange, contentDescription = null)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).width(200.dp),
            shape = RoundedCornerShape(24.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            dateFilters.forEachIndexed { index, filter ->
                DropdownMenuItem(
                    text = {
                        Text(text = filter)
                    },
                    onClick = {
                        selectedDateFilter = index
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

private fun Map<String, Int>.percentage(key: String): Float {
    return (this[key]?.toFloat()?.div(this.values.sum().toFloat()))?.toFloat()?.times(100) ?: 0f
}

@Composable
fun StatsDistribution(
    modifier: Modifier = Modifier,
    stat: String,
    values: Map<String, Int>,
    icons: Map<String, ImageVector>
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
    ) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = stat,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
            values.keys.forEach { key ->
                logger.info { "the key from the values is ${key} and the keys available are ${icons.keys}" }
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.weight(1f),
                        imageVector = icons[key] as ImageVector,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(8.dp).weight(1f),
                        text = key,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = .6f)
                    )
                    LinearProgressIndicator(
                        progress = { values.percentage(key) / 100 },
                        modifier = Modifier.padding(8.dp).weight(2f),
                        color = Color.DarkGray,
                        trackColor = Color.Gray.copy(alpha = 0.4f),
                        strokeCap = StrokeCap.Butt,
                        gapSize = 8.dp
                    )
                    Text(
                        modifier = Modifier.padding(8.dp).weight(1f),
                        text = "${values.percentage(key)}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = .6f)
                    )
                }
            }
        }
    }
}

@Composable
fun LaravelTab() {
    var laraStats = listOf<Map<String, Any>>(
        mapOf<String, Any>("name" to "Total Logs", "value" to 899),
        mapOf<String, Any>("name" to "Errors", "value" to 899),
        mapOf<String, Any>("name" to "Info", "value" to 899),
        mapOf<String, Any>("name" to "Warning", "value" to 899),
        mapOf<String, Any>("name" to "Debug", "value" to 899),
    )
    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            DateFilter()
        }
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).height(225.dp))  {

            laraStats.forEach{ i ->
                CountStatCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .height(200.dp)
                        .width(150.dp),
                    statName =i["name"].toString(),
                    statValue = i["value"].toString().toInt(),
                    statPercentage = Random.nextInt(100),
                )
            }
        }

        AddDefaultPieChart()
    }
}

@Composable
fun LogsTerminal() {
    Surface(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Column(modifier = Modifier) {
            Terminal()
        }
    }
}

@Composable
fun Settings() {
    Surface(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Column(modifier = Modifier) {
            Text("This is the Settings")
        }
    }
}
