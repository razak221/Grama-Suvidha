package com.example.myapplication.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Project
import com.example.myapplication.ui.components.AppEmptyState
import com.example.myapplication.ui.components.AsyncProjectImage
import com.example.myapplication.ui.components.BeforeAfterSlider
import com.example.myapplication.viewmodel.ProjectFilter
import com.example.myapplication.viewmodel.ProjectSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    allProjectsCount: Int,
    projects: List<Project>,
    isKannada: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    searchQuery: String,
    currentFilter: ProjectFilter,
    currentSort: ProjectSort,
    onProjectClick: (Project) -> Unit,
    onToggleLanguage: () -> Unit,
    onSearchChange: (String) -> Unit,
    onFilterChange: (ProjectFilter) -> Unit,
    onSortChange: (ProjectSort) -> Unit,
    onRetry: () -> Unit
) {
    val filterOptions = listOf(
        ProjectFilter.ALL to (if (isKannada) "ಎಲ್ಲಾ" else "All"),
        ProjectFilter.IN_PROGRESS to (if (isKannada) "ಪ್ರಗತಿಯಲ್ಲಿ" else "In Progress"),
        ProjectFilter.COMPLETED to (if (isKannada) "ಪೂರ್ಣಗೊಂಡಿದೆ" else "Completed"),
        ProjectFilter.NOT_STARTED to (if (isKannada) "ಪ್ರಾರಂಭವಾಗಿಲ್ಲ" else "Not Started")
    )
    val sortOptions = listOf(
        ProjectSort.DEFAULT to (if (isKannada) "ಡೀಫಾಲ್ಟ್" else "Default"),
        ProjectSort.PROGRESS_DESC to (if (isKannada) "ಹೆಚ್ಚು ಪ್ರಗತಿ" else "Highest Progress"),
        ProjectSort.PROGRESS_ASC to (if (isKannada) "ಕಡಿಮೆ ಪ್ರಗತಿ" else "Lowest Progress")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isKannada) "ಯೋಜನೆಗಳು" else "Projects",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isKannada) "ಹುಡುಕಿ, ಫಿಲ್ಟರ್ ಮಾಡಿ, ವಿವರಗಳನ್ನು ನೋಡಿ" else "Search, filter, and inspect details",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = onToggleLanguage,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(if (isKannada) "English" else "ಕನ್ನಡ")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
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
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = if (isKannada) "ಸಾರ್ವಜನಿಕ ಕೆಲಸಗಳ ನೋಟಿಸ್ ಬೋರ್ಡ್" else "Public Works Notice Board",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isKannada) {
                                "${projects.size} ಫಲಿತಾಂಶಗಳು, ಒಟ್ಟು $allProjectsCount ಯೋಜನೆಗಳಲ್ಲಿ"
                            } else {
                                "${projects.size} results from $allProjectsCount total projects"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(if (isKannada) "ಯೋಜನೆಗಳನ್ನು ಹುಡುಕಿ" else "Search projects") },
                    placeholder = { Text(if (isKannada) "ಹೆಸರು, ಸ್ಥಿತಿ, ಬಜೆಟ್..." else "Name, status, budget...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp)
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = if (isKannada) "ಫಿಲ್ಟರ್" else "Filter",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filterOptions.forEach { (filter, label) ->
                            FilterChip(
                                selected = currentFilter == filter,
                                onClick = { onFilterChange(filter) },
                                label = { Text(label) },
                                leadingIcon = if (currentFilter == filter) {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = if (isKannada) "ವಿಂಗಡಿಸು" else "Sort",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        sortOptions.forEach { (sort, label) ->
                            AssistChip(
                                onClick = { onSortChange(sort) },
                                label = { Text(label) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = if (currentSort == sort) {
                                        MaterialTheme.colorScheme.secondaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    },
                                    labelColor = if (currentSort == sort) {
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            )
                        }
                    }
                }
            }

            if (isLoading && allProjectsCount == 0) {
                items(3) {
                    LoadingProjectCard()
                }
            } else if (errorMessage != null && allProjectsCount == 0) {
                item {
                    AppEmptyState(
                        title = if (isKannada) "ಯೋಜನೆಗಳನ್ನು ಲೋಡ್ ಮಾಡಲಾಗಲಿಲ್ಲ" else "Couldn't load projects",
                        subtitle = if (isKannada) {
                            "ಸ್ಥಳೀಯ ಡೇಟಾವನ್ನು ಓದುವಲ್ಲಿ ತೊಂದರೆ ಎದುರಾಯಿತು. ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ."
                        } else {
                            "There was a problem reading the local project data. Try again."
                        },
                        actionLabel = if (isKannada) "ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ" else "Try again",
                        onAction = onRetry
                    )
                }
            } else if (projects.isEmpty()) {
                item {
                    AppEmptyState(
                        title = if (isKannada) "ಹೊಂದುವ ಯೋಜನೆಗಳು ಕಂಡುಬಂದಿಲ್ಲ" else "No matching projects",
                        subtitle = if (isKannada) {
                            "ಹುಡುಕಾಟ ಅಥವಾ ಫಿಲ್ಟರ್ ಆಯ್ಕೆಗಳನ್ನು ಬದಲಾಯಿಸಿ."
                        } else {
                            "Try adjusting your search query or the selected filters."
                        }
                    )
                }
            } else {
                items(projects, key = { it.id }) { project ->
                    ProjectItem(project = project, isKannada = isKannada, onClick = onProjectClick)
                }
            }
        }
    }
}

@Composable
private fun LoadingProjectCard() {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(strokeWidth = 2.5.dp)
            }
            Column(modifier = Modifier.padding(18.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(18.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(50))
                )
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(50))
                )
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(0.7f)
                        .height(10.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(50))
                )
            }
        }
    }
}

@Composable
fun ProjectItem(project: Project, isKannada: Boolean, onClick: (Project) -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(project.id) { startAnimation = true }

    val animatedProgress by animateFloatAsState(
        targetValue = if (startAnimation) project.progress / 100f else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "project_progress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(project) },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                AsyncProjectImage(
                    imageUrl = project.beforeImageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.72f))
                            )
                        )
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (isKannada) project.statusKn else project.statusEn,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = if (isKannada) project.titleKn else project.titleEn,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = if (isKannada) project.descriptionKn else project.descriptionEn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.92f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = { },
                        enabled = false,
                        label = { Text("${if (isKannada) "ಬಜೆಟ್" else "Budget"}: ${project.budget}") }
                    )
                    AssistChip(
                        onClick = { },
                        enabled = false,
                        label = {
                            Text(
                                "${if (isKannada) "ಪೂರ್ಣಗೊಳಿಸುವಿಕೆ" else "Due"}: ${project.expectedCompletion}"
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isKannada) "ಪ್ರಗತಿ" else "Progress",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${project.progress}%",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(999.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer
                )
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
    var rating by rememberSaveable(project.id) { mutableIntStateOf(0) }
    var issueReported by rememberSaveable(project.id) { mutableStateOf(false) }
    var imageAttached by rememberSaveable(project.id) { mutableStateOf(false) }
    var issueText by rememberSaveable(project.id) { mutableStateOf("") }
    var startAnimation by remember(project.id) { mutableStateOf(false) }

    LaunchedEffect(project.id) { startAnimation = true }

    val animatedProgress by animateFloatAsState(
        targetValue = if (startAnimation) project.progress / 100f else 0f,
        animationSpec = tween(durationMillis = 1200),
        label = "project_detail_progress"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKannada) "ಯೋಜನೆಯ ವಿವರಗಳು" else "Project Details") },
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
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                if (project.afterImageUrl != null) {
                    BeforeAfterSlider(
                        beforeImageUrl = project.beforeImageUrl,
                        afterImageUrl = project.afterImageUrl,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        beforeLabel = if (isKannada) "ಮೊದಲು" else "Before",
                        afterLabel = if (isKannada) "ನಂತರ" else "After"
                    )
                } else {
                    AsyncProjectImage(
                        imageUrl = project.beforeImageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(24.dp))
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = if (isKannada) project.titleKn else project.titleEn,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = if (isKannada) project.descriptionKn else project.descriptionEn,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 18.dp))

                        DetailInfoRow(if (isKannada) "ಬಜೆಟ್" else "Budget", project.budget)
                        DetailInfoRow(
                            if (isKannada) "ನಿರೀಕ್ಷಿತ ಪೂರ್ಣಗೊಳಿಸುವಿಕೆ" else "Expected Completion",
                            project.expectedCompletion
                        )
                        DetailInfoRow(
                            if (isKannada) "ಸ್ಥಿತಿ" else "Status",
                            if (isKannada) project.statusKn else project.statusEn
                        )

                        Text(
                            text = if (isKannada) "ಅಧಿಕೃತ ಪ್ರಗತಿ" else "Official Progress",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(14.dp)
                                .padding(top = 12.dp)
                                .clip(RoundedCornerShape(999.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primaryContainer
                        )
                        Text(
                            text = "${project.progress}% ${if (isKannada) "ಪೂರ್ಣಗೊಂಡಿದೆ" else "completed"}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = if (isKannada) "ನಾಗರಿಕರ ಪ್ರತಿಕ್ರಿಯೆ" else "Citizen Feedback",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isKannada) {
                                "ಈ ಯೋಜನೆಯ ಗುಣಮಟ್ಟದ ಬಗ್ಗೆ ನಿಮ್ಮ ಅಭಿಪ್ರಾಯ ಹಂಚಿಕೊಳ್ಳಿ."
                            } else {
                                "Share how this project feels on the ground."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 6.dp)
                        )

                        Row(
                            modifier = Modifier.padding(top = 18.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(5) { index ->
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable { rating = index + 1 },
                                    tint = if (index < rating) Color(0xFFFFB300) else {
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
                                    }
                                )
                            }
                        }

                        if (rating > 0) {
                            Text(
                                text = if (isKannada) {
                                    "ನಿಮ್ಮ ರೇಟಿಂಗ್: $rating/5"
                                } else {
                                    "Your rating: $rating/5"
                                },
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

                        if (!issueReported) {
                            Text(
                                text = if (isKannada) "ಸಮಸ್ಯೆಯನ್ನು ವಿವರಿಸಿ" else "Describe an issue",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            OutlinedTextField(
                                value = issueText,
                                onValueChange = { issueText = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(top = 12.dp),
                                placeholder = { Text(if (isKannada) "ಇಲ್ಲಿ ಟೈಪ್ ಮಾಡಿ..." else "Type here...") },
                                shape = RoundedCornerShape(18.dp)
                            )

                            OutlinedButton(
                                onClick = { imageAttached = !imageAttached },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 14.dp),
                                shape = RoundedCornerShape(18.dp)
                            ) {
                                Text(
                                    if (imageAttached) {
                                        if (isKannada) "ಚಿತ್ರ ಲಗತ್ತಿಸಲಾಗಿದೆ" else "Image attached"
                                    } else {
                                        if (isKannada) "ಚಿತ್ರವನ್ನು ಲಗತ್ತಿಸಿ" else "Attach image"
                                    }
                                )
                            }

                            Button(
                                onClick = { issueReported = issueText.isNotBlank() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .padding(top = 14.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                enabled = issueText.isNotBlank()
                            ) {
                                Text(
                                    text = if (isKannada) "ಸಮಸ್ಯೆಯನ್ನು ವರದಿ ಮಾಡಿ" else "Report Issue",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = MaterialTheme.colorScheme.errorContainer
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                    Text(
                                        text = if (isKannada) {
                                            "ಧನ್ಯವಾದಗಳು. ನಿಮ್ಮ ಪ್ರತಿಕ್ರಿಯೆಯನ್ನು ಸಂಬಂಧಪಟ್ಟ ಅಧಿಕಾರಿಗಳಿಗೆ ಕಳುಹಿಸಲಾಗಿದೆ."
                                        } else {
                                            "Thanks. Your feedback has been sent to the concerned authorities."
                                        },
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
