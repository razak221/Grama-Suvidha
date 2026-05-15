package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Project
import com.example.myapplication.ui.components.AppEmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    projects: List<Project>,
    isKannada: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    onReportIssue: () -> Unit,
    onNewProject: () -> Unit,
    onCommunityFeed: () -> Unit
) {
    val totalProjects = projects.size
    val completedProjects = projects.count { it.progress == 100 }
    val inProgressProjects = projects.count { it.progress in 1..99 }
    val notStartedProjects = projects.count { it.progress == 0 }
    val avgProgress = if (totalProjects > 0) projects.sumOf { it.progress } / totalProjects else 0
    val spotlightProject = projects
        .filter { it.progress < 100 }
        .minByOrNull { it.expectedCompletion }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isKannada) "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್" else "Dashboard",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isKannada) "ಗ್ರಾಮದ ಪ್ರಗತಿ ಒಂದು ನೋಟದಲ್ಲಿ" else "Village progress at a glance",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .navigationBarsPadding(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroOverviewCard(isKannada = isKannada, isLoading = isLoading, totalProjects = totalProjects)
            }

            if (isLoading && projects.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(28.dp), strokeWidth = 2.5.dp)
                            Column {
                                Text(
                                    text = if (isKannada) "ಡೇಟಾವನ್ನು ಲೋಡ್ ಮಾಡಲಾಗುತ್ತಿದೆ" else "Loading project data",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isKannada) "ಸ್ವಲ್ಪ ಕ್ಷಣ ನಿರೀಕ್ಷಿಸಿ" else "This should only take a moment",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            if (errorMessage != null && projects.isEmpty()) {
                item {
                    AppEmptyState(
                        title = if (isKannada) "ಯೋಜನೆಗಳನ್ನು ಲೋಡ್ ಮಾಡಲಾಗಲಿಲ್ಲ" else "Couldn't load projects",
                        subtitle = if (isKannada) {
                            "ದಯವಿಟ್ಟು ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ. ಸ್ಥಳೀಯ ಡೇಟಾ ಓದುವಲ್ಲಿ ತೊಂದರೆ ಎದುರಾಯಿತು."
                        } else {
                            "Please try again. There was a problem reading the local project data."
                        },
                        actionLabel = if (isKannada) "ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ" else "Try again",
                        onAction = onRetry
                    )
                }
            } else if (projects.isEmpty() && !isLoading) {
                item {
                    AppEmptyState(
                        title = if (isKannada) "ಯೋಜನೆಗಳು ಲಭ್ಯವಿಲ್ಲ" else "No projects available",
                        subtitle = if (isKannada) {
                            "ಹೊಸ ಯೋಜನೆಗಳು ಸೇರ್ಪಡೆಯಾದಾಗ ಇಲ್ಲಿ ಕಾಣುತ್ತವೆ."
                        } else {
                            "New civic projects will appear here as soon as they are added."
                        }
                    )
                }
            } else {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            StatCard(
                                title = if (isKannada) "ಒಟ್ಟು ಯೋಜನೆಗಳು" else "Total Projects",
                                value = totalProjects.toString(),
                                icon = Icons.Default.Info,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                title = if (isKannada) "ಪೂರ್ಣಗೊಂಡಿದೆ" else "Completed",
                                value = completedProjects.toString(),
                                icon = Icons.Default.CheckCircle,
                                color = Color(0xFF1E8E5A),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            StatCard(
                                title = if (isKannada) "ಪ್ರಗತಿಯಲ್ಲಿದೆ" else "In Progress",
                                value = inProgressProjects.toString(),
                                icon = Icons.Default.Build,
                                color = Color(0xFFB56A14),
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                title = if (isKannada) "ಪ್ರಾರಂಭವಾಗಿಲ್ಲ" else "Not Started",
                                value = notStartedProjects.toString(),
                                icon = Icons.Default.Warning,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = if (isKannada) "ಸರಾಸರಿ ಪ್ರಗತಿ" else "Average Progress",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isKannada) "ಎಲ್ಲಾ ಯೋಜನೆಗಳ ಸಮಗ್ರ ಸ್ಥಿತಿ" else "Combined progress across all projects",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Text(
                                    text = "$avgProgress%",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            LinearProgressIndicator(
                                progress = { avgProgress / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .padding(top = 18.dp),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    }
                }

                spotlightProject?.let { project ->
                    item {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.45f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = if (isKannada) "ಗಮನಿಸಬೇಕಾದ ಯೋಜನೆ" else "Spotlight Project",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                                Text(
                                    text = if (isKannada) project.titleKn else project.titleEn,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 6.dp)
                                )
                                Text(
                                    text = if (isKannada) project.descriptionKn else project.descriptionEn,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Text(
                                    text = if (isKannada) {
                                        "ನಿರೀಕ್ಷಿತ ಪೂರ್ಣಗೊಳಿಸುವಿಕೆ: ${project.expectedCompletion}"
                                    } else {
                                        "Expected completion: ${project.expectedCompletion}"
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 14.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = if (isKannada) "ತ್ವರಿತ ಕ್ರಿಯೆಗಳು" else "Quick Actions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        QuickActionButton(
                            icon = Icons.Default.Warning,
                            label = if (isKannada) "ಸಮಸ್ಯೆ ವರದಿ" else "Report Issue",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(1f),
                            onClick = onReportIssue
                        )
                        QuickActionButton(
                            icon = Icons.Default.AddCircle,
                            label = if (isKannada) "ಹೊಸ ಯೋಜನೆ" else "New Project",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f),
                            onClick = onNewProject
                        )
                    }

                    Button(
                        onClick = onCommunityFeed,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = if (isKannada) "ಸಮುದಾಯ ಫೀಡ್ ತೆರೆಸಿ" else "Open Community Feed",
                            modifier = Modifier.padding(start = 8.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroOverviewCard(
    isKannada: Boolean,
    isLoading: Boolean,
    totalProjects: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(22.dp)
    ) {
        Column {
            Text(
                text = if (isKannada) "ನಿಮ್ಮ ಗ್ರಾಮದ ಕಾರ್ಯಗಳು ಈಗ ಹೆಚ್ಚು ಸ್ಪಷ್ಟ" else "Village work is easier to follow now",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = if (isKannada) {
                    "ಪ್ರಗತಿ, ದೂರುಗಳು ಮತ್ತು ಸಮುದಾಯ ಪ್ರತಿಕ್ರಿಯೆಗಳನ್ನು ಒಂದೇ ಜಾಗದಲ್ಲಿ ನೋಡಿ."
                } else {
                    "See project status, issue reporting, and community feedback in one place."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = if (isLoading) {
                    if (isKannada) "ಡೇಟಾ ನವೀಕರಿಸಲಾಗುತ್ತಿದೆ..." else "Refreshing local data..."
                } else {
                    if (isKannada) "$totalProjects ಯೋಜನೆಗಳು ವೀಕ್ಷಣೆಗೆ ಸಿದ್ಧ" else "$totalProjects projects ready to explore"
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(72.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color.copy(alpha = 0.12f))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = null, tint = color)
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = color,
                modifier = Modifier.padding(top = 14.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
