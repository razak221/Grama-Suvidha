package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isKannada: Boolean,
    onBack: () -> Unit
) {
    var dataSaver by rememberSaveable { mutableStateOf(false) }
    var autoSync by rememberSaveable { mutableStateOf(true) }
    var pushNotifications by rememberSaveable { mutableStateOf(true) }
    var locationSharing by rememberSaveable { mutableStateOf(false) }
    var analytics by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKannada) "ಸೆಟ್ಟಿಂಗ್‌ಗಳು" else "Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SettingsSectionCard(
                    title = if (isKannada) "ಸಾಮಾನ್ಯ" else "General"
                ) {
                    ListItem(
                        headlineContent = { Text(if (isKannada) "ಡೇಟಾ ಸೇವರ್" else "Data Saver") },
                        supportingContent = {
                            Text(
                                if (isKannada) {
                                    "ಕಡಿಮೆ ಡೇಟಾ ಬಳಕೆಗೆ ಚಿತ್ರಗಳ ಗುಣಮಟ್ಟವನ್ನು ಕಡಿಮೆ ಮಾಡುತ್ತದೆ"
                                } else {
                                    "Reduces image quality to use less data"
                                }
                            )
                        },
                        trailingContent = {
                            Switch(checked = dataSaver, onCheckedChange = { dataSaver = it })
                        }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text(if (isKannada) "ಸ್ವಯಂ ಸಿಂಕ್" else "Auto-Sync") },
                        supportingContent = {
                            Text(
                                if (isKannada) "ಹಿನ್ನೆಲೆಯಲ್ಲಿ ಡೇಟಾವನ್ನು ಪರಿಶೀಲಿಸುತ್ತದೆ" else "Checks for fresh data in the background"
                            )
                        },
                        trailingContent = {
                            Switch(checked = autoSync, onCheckedChange = { autoSync = it })
                        }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text(if (isKannada) "ಪುಶ್ ಅಧಿಸೂಚನೆಗಳು" else "Push Notifications") },
                        supportingContent = {
                            Text(
                                if (isKannada) "ಯೋಜನೆ ಹಾಗೂ ದೂರುಗಳ ನವೀಕರಣಗಳನ್ನು ಪಡೆಯಿರಿ" else "Receive project and issue updates"
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = pushNotifications,
                                onCheckedChange = { pushNotifications = it }
                            )
                        }
                    )
                }
            }

            item {
                SettingsSectionCard(
                    title = if (isKannada) "ಗೌಪ್ಯತೆ" else "Privacy"
                ) {
                    ListItem(
                        headlineContent = { Text(if (isKannada) "ಸ್ಥಳ ಹಂಚಿಕೆ" else "Location Sharing") },
                        supportingContent = {
                            Text(
                                if (isKannada) {
                                    "ದೂರುಗಳಿಗೆ ಸ್ಥಳದ ಮಾಹಿತಿಯನ್ನು ಸ್ವಯಂಚಾಲಿತವಾಗಿ ಸೇರಿಸಬಹುದು"
                                } else {
                                    "Allows issue reports to include location details automatically"
                                }
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = locationSharing,
                                onCheckedChange = { locationSharing = it }
                            )
                        }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text(if (isKannada) "ಬಳಕೆ ವಿಶ್ಲೇಷಣೆ" else "Usage Analytics") },
                        supportingContent = {
                            Text(
                                if (isKannada) "ಅನಾಮಧೇಯ ಬಳಕೆ ಮಾಹಿತಿಯಿಂದ ಅಪ್ಲಿಕೇಶನ್ ಸುಧಾರಿಸುತ್ತದೆ" else "Anonymous usage insights help improve the app"
                            )
                        },
                        trailingContent = {
                            Switch(checked = analytics, onCheckedChange = { analytics = it })
                        }
                    )
                }
            }

            item {
                SettingsSectionCard(
                    title = if (isKannada) "ಬಗ್ಗೆ" else "About"
                ) {
                    ListItem(
                        headlineContent = { Text(if (isKannada) "ಆವೃತ್ತಿ" else "Version") },
                        supportingContent = { Text("1.0.0") }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text(if (isKannada) "ಡೆವಲಪರ್" else "Developer") },
                        supportingContent = { Text("Razak Ahmed Khan • MindMatrix VTU Internship") }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp)
        )
        content()
    }
}
