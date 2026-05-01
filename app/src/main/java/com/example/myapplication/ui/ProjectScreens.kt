package com.example.myapplication.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.model.Project

/**
 * Enhanced UI for Grama-Suvidha Portal - Digital Notice Board
 * Focused on transparency, community ownership, and professional aesthetics for rural infrastructure tracking.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    projects: List<Project>,
    isKannada: Boolean,
    onProjectClick: (Project) -> Unit,
    onToggleLanguage: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isKannada) "ಗ್ರಾಮ-ಸುವಿಧಾ" else "Grama-Suvidha",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isKannada) "ಡಿಜಿಟಲ್ ನೋಟಿಸ್ ಬೋರ್ಡ್" else "Digital Notice Board",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = onToggleLanguage,
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (isKannada) "English" else "ಕನ್ನಡ",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(projects) { project ->
                ProjectItem(project, isKannada, onProjectClick)
            }
        }
    }
}

@Composable
fun ProjectItem(project: Project, isKannada: Boolean, onClick: (Project) -> Unit) {
    // Animate the progress bar when the item appears
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimation = true }
    
    val animatedProgress by animateFloatAsState(
        targetValue = if (startAnimation) project.progress / 100f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 100),
        label = "progress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(project) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
    ) {
        Column {
            Box(modifier = Modifier.height(180.dp).fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(project.beforeImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Gradient overlay for better text visibility and modern look
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                )
                // Status Badge
                Surface(
                    modifier = Modifier.padding(12.dp).align(Alignment.TopEnd),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (isKannada) project.statusKn else project.statusEn,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isKannada) project.titleKn else project.titleEn,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${if (isKannada) "ಬಜೆಟ್" else "Budget"}: ${project.budget}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                
                // Progress Bar Section
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isKannada) "ಪ್ರಗತಿ" else "Progress",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${project.progress}%",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    project: Project,
    isKannada: Boolean,
    onBack: () -> Unit
) {
    var rating by remember { mutableIntStateOf(0) }
    var issueReported by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimation = true }
    
    val animatedProgress by animateFloatAsState(
        targetValue = if (startAnimation) project.progress / 100f else 0f,
        animationSpec = tween(durationMillis = 1500, delayMillis = 300),
        label = "progress_detail"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKannada) "ಯೋಜನೆಯ ವಿವರಗಳು" else "Project Details") },
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
                .verticalScroll(scrollState)
        ) {
            // "Before & After" Visual Comparison
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isKannada) "ಪ್ರಗತಿ ಚಿತ್ರಗಳು" else "Visual Progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Before Image
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isKannada) "ಮೊದಲು" else "Before",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(bottom = 4.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(project.beforeImageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Before Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    // After/Current Image
                    if (project.afterImageUrl != null || project.progress > 0) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isKannada) "ನಂತರ/ಈಗ" else "After/Current",
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(bottom = 4.dp),
                                color = MaterialTheme.colorScheme.secondary
                            )
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(project.afterImageUrl ?: project.beforeImageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "After Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            // Project Description Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = if (isKannada) project.titleKn else project.titleEn,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (isKannada) project.descriptionKn else project.descriptionEn,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Detailed Information List
            Column(modifier = Modifier.padding(20.dp)) {
                DetailInfoRow(if (isKannada) "ಬಜೆಟ್" else "Budget", project.budget)
                DetailInfoRow(if (isKannada) "ನಿರೀಕ್ಷಿತ ಪೂರ್ಣಗೊಳಿಸುವಿಕೆ" else "Expected Completion", project.expectedCompletion)
                DetailInfoRow(if (isKannada) "ಸ್ಥಿತಿ" else "Status", if (isKannada) project.statusKn else project.statusEn)

                Spacer(modifier = Modifier.height(32.dp))
                
                // Progress Tracker
                Text(
                    text = if (isKannada) "ಅಧಿಕೃತ ಪ್ರಗತಿ ವರದಿ" else "Official Progress Report",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(7.dp)),
                )
                Text(
                    text = "${project.progress}% ${if (isKannada) "ಕಾಮಗಾರಿ ಪೂರ್ಣಗೊಂಡಿದೆ" else "Work Completed"}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(40.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(24.dp))
                
                // Feedback Section - "Citizen's Voice"
                Text(
                    text = if (isKannada) "ನಾಗರಿಕರ ಧ್ವನಿ" else "Citizen's Voice",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = if (isKannada) "ಈ ಯೋಜನೆಯ ಗುಣಮಟ್ಟವನ್ನು ರೇಟ್ ಮಾಡಿ" else "Rate the quality of this project",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
                
                // Star Rating
                Row(modifier = Modifier.padding(vertical = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star ${index + 1}",
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { rating = index + 1 },
                            tint = if (index < rating) Color(0xFFFFD700) else Color.LightGray
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Issue Reporting
                Button(
                    onClick = { issueReported = true },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (isKannada) "ಸಮಸ್ಯೆಯನ್ನು ವರದಿ ಮಾಡಿ" else "Report an Issue",
                        fontWeight = FontWeight.Bold
                    )
                }
                
                if (issueReported) {
                    Card(
                        modifier = Modifier.padding(top = 20.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = if (isKannada) "ಧನ್ಯವಾದಗಳು! ನಿಮ್ಮ ದೂರನ್ನು ಸಂಬಂಧಪಟ್ಟ ಅಧಿಕಾರಿಗಳಿಗೆ ಕಳುಹಿಸಲಾಗಿದೆ." 
                                   else "Thank you! Your report has been sent to the concerned authorities.",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
