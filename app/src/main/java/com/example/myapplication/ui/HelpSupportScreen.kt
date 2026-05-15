package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(
    isKannada: Boolean,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKannada) "ಸಹಾಯ ಮತ್ತು ಬೆಂಬಲ" else "Help & Support") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Contact Section
            Text(
                text = if (isKannada) "ಸಂಪರ್ಕ ಮಾಹಿತಿ" else "Contact Information",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.padding(bottom = 12.dp)) {
                        Icon(Icons.Default.Call, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = if (isKannada) "ಟೋಲ್ ಫ್ರೀ" else "Toll Free",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text("1800-425-XXXX", fontWeight = FontWeight.Bold)
                        }
                    }
                    HorizontalDivider()
                    Row(modifier = Modifier.padding(vertical = 12.dp)) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = if (isKannada) "ಇ-ಮೇಲ್" else "Email",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text("support@gramasuvidha.gov.in", fontWeight = FontWeight.Bold)
                        }
                    }
                    HorizontalDivider()
                    Row(modifier = Modifier.padding(top = 12.dp)) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = if (isKannada) "ಕಚೇರಿ" else "Office",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (isKannada) "ಗ್ರಾಮ ಪಂಚಾಯತ್ ಕಚೇರಿ, ಮುಖ್ಯ ರಸ್ತೆ" else "Gram Panchayat Office, Main Road",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // FAQ Section
            Text(
                text = if (isKannada) "ಪದೇ ಪದೇ ಕೇಳಲಾಗುವ ಪ್ರಶ್ನೆಗಳು" else "Frequently Asked Questions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val faqs = listOf(
                Pair(
                    if (isKannada) "ನನ್ನ ದೂರನ್ನು ಹೇಗೆ ಟ್ರ್ಯಾಕ್ ಮಾಡುವುದು?" else "How to track my complaint?",
                    if (isKannada) "ನೀವು 'ವರದಿಗಳು' ವಿಭಾಗದಲ್ಲಿ ಟ್ರ್ಯಾಕ್ ಮಾಡಬಹುದು. ಪ್ರತಿ ವರದಿಗೆ ಅನನ್ಯ ID ನೀಡಲಾಗುತ್ತದೆ." else "You can track it in the 'Reports' section of your profile. Each report is given a unique ID."
                ),
                Pair(
                    if (isKannada) "ಹೊಸ ಯೋಜನೆಯನ್ನು ವಿನಂತಿಸುವುದು ಹೇಗೆ?" else "How to request a new project?",
                    if (isKannada) "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್‌ನಲ್ಲಿರುವ 'ಹೊಸ ಯೋಜನೆ' ಬಟನ್ ಬಳಸಿ. ಪಂಚಾಯತ್ ಸಮಿತಿ ಪರಿಶೀಲಿಸುತ್ತದೆ." else "Use the 'New Project' button on the Dashboard. The Panchayat committee will review your proposal."
                ),
                Pair(
                    if (isKannada) "ಯೋಜನೆ ಪ್ರಗತಿ ಹೇಗೆ ನವೀಕರಿಸಲಾಗುತ್ತದೆ?" else "How is project progress updated?",
                    if (isKannada) "ಅಧಿಕಾರಿಗಳು ಪ್ರತಿ ವಾರ ಅಧಿಕೃತ ಪ್ರಗತಿ ನವೀಕರಿಸುತ್ತಾರೆ. ನೀವು ಅಧಿಸೂಚನೆಗಳನ್ನು ಸಹ ಸ್ವೀಕರಿಸಬಹುದು." else "Officials update official progress every week. You can also receive notifications for changes."
                ),
                Pair(
                    if (isKannada) "ಭಾಷೆಯನ್ನು ಬದಲಾಯಿಸುವುದು ಹೇಗೆ?" else "How to change the language?",
                    if (isKannada) "ಪ್ರೊಫೈಲ್ ಪುಟದಲ್ಲಿ 'ಭಾಷೆ ಬದಲಾಯಿಸಿ' ಬಟನ್ ಒತ್ತಿ ಅಥವಾ ಯೋಜನೆಗಳ ಪುಟದಲ್ಲಿ ಭಾಷಾ ಟಾಗಲ್ ಬಳಸಿ." else "Press the 'Change Language' button on the Profile page or use the language toggle on the Projects page."
                ),
                Pair(
                    if (isKannada) "ಆಫ್‌ಲೈನ್‌ನಲ್ಲಿ ಅಪ್ಲಿಕೇಶನ್ ಕೆಲಸ ಮಾಡುತ್ತದೆಯೇ?" else "Does the app work offline?",
                    if (isKannada) "ಹೌದು! ಒಮ್ಮೆ ಡೇಟಾ ಲೋಡ್ ಆದ ನಂತರ, ಅದನ್ನು ಸ್ಥಳೀಯವಾಗಿ ಕ್ಯಾಶ್ ಮಾಡಲಾಗುತ್ತದೆ. ಇಂಟರ್ನೆಟ್ ಇಲ್ಲದೆಯೂ ಯೋಜನೆಗಳನ್ನು ವೀಕ್ಷಿಸಬಹುದು." else "Yes! Once data is loaded, it is cached locally. You can view projects even without internet."
                )
            )

            faqs.forEach { faq ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = faq.first,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = faq.second,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
