package com.example.myapplication.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CommunityIssue(
    val id: Int,
    val titleEn: String,
    val titleKn: String,
    val descEn: String,
    val descKn: String,
    val locationEn: String,
    val locationKn: String,
    val reporterEn: String,
    val reporterKn: String,
    val statusEn: String,
    val statusKn: String,
    val category: String,
    val timeAgo: String,
    val upvotes: Int,
    val comments: Int,
    val icon: ImageVector,
    val statusColor: Color
)

data class CommunityProject(
    val id: Int,
    val titleEn: String,
    val titleKn: String,
    val descEn: String,
    val descKn: String,
    val budgetEn: String,
    val budgetKn: String,
    val progress: Int,
    val statusEn: String,
    val statusKn: String,
    val departmentEn: String,
    val departmentKn: String,
    val category: String,
    val statusColor: Color
)

private fun getSampleIssues() = listOf(
    CommunityIssue(1, "Broken Street Light", "ಮುರಿದ ಬೀದಿ ದೀಪ", "Street light on Main Road not working for 2 weeks", "ಮುಖ್ಯ ರಸ್ತೆಯ ಬೀದಿ ದೀಪ 2 ವಾರಗಳಿಂದ ಕೆಲಸ ಮಾಡುತ್ತಿಲ್ಲ", "Main Road, Ward 3", "ಮುಖ್ಯ ರಸ್ತೆ, ವಾರ್ಡ್ 3", "Ramesh K.", "ರಮೇಶ್ ಕೆ.", "Under Review", "ಪರಿಶೀಲನೆಯಲ್ಲಿ", "Infrastructure", "2h ago", 12, 4, Icons.Default.Warning, Color(0xFFF59E0B)),
    CommunityIssue(2, "Water Pipeline Leak", "ನೀರಿನ ಪೈಪ್‌ಲೈನ್ ಸೋರಿಕೆ", "Major water leak near the village well causing wastage", "ಗ್ರಾಮದ ಬಾವಿಯ ಬಳಿ ದೊಡ್ಡ ನೀರಿನ ಸೋರಿಕೆ", "Near Village Well", "ಗ್ರಾಮದ ಬಾವಿ ಬಳಿ", "Lakshmi S.", "ಲಕ್ಷ್ಮಿ ಎಸ್.", "In Progress", "ಪ್ರಗತಿಯಲ್ಲಿ", "Water", "5h ago", 28, 15, Icons.Default.Build, Color(0xFF3B82F6)),
    CommunityIssue(3, "Garbage Dump Issue", "ಕಸದ ರಾಶಿ ಸಮಸ್ಯೆ", "Unauthorized garbage dump near school area", "ಶಾಲೆಯ ಬಳಿ ಅನಧಿಕೃತ ಕಸದ ರಾಶಿ", "School Road, Ward 1", "ಶಾಲೆ ರಸ್ತೆ, ವಾರ್ಡ್ 1", "Suresh M.", "ಸುರೇಶ್ ಎಮ್.", "Resolved", "ಪರಿಹರಿಸಲಾಗಿದೆ", "Sanitation", "1d ago", 35, 8, Icons.Default.CheckCircle, Color(0xFF10B981)),
    CommunityIssue(4, "Road Pothole", "ರಸ್ತೆ ಗುಂಡಿ", "Large pothole on bus route causing accidents", "ಬಸ್ ಮಾರ್ಗದಲ್ಲಿ ದೊಡ್ಡ ಗುಂಡಿ", "Bus Stand Road", "ಬಸ್ ನಿಲ್ದಾಣ ರಸ್ತೆ", "Priya R.", "ಪ್ರಿಯಾ ಆರ್.", "Reported", "ವರದಿ ಮಾಡಲಾಗಿದೆ", "Roads", "3h ago", 19, 2, Icons.Default.Info, Color(0xFFEF4444)),
    CommunityIssue(5, "Stray Animal Menace", "ಬೀದಿ ಪ್ರಾಣಿ ಕಾಟ", "Stray dogs causing problems near market area", "ಮಾರುಕಟ್ಟೆ ಪ್ರದೇಶದ ಬಳಿ ಬೀದಿ ನಾಯಿಗಳ ಕಾಟ", "Market Area", "ಮಾರುಕಟ್ಟೆ ಪ್ರದೇಶ", "Kavitha N.", "ಕವಿತಾ ಎನ್.", "Under Review", "ಪರಿಶೀಲನೆಯಲ್ಲಿ", "Safety", "6h ago", 8, 1, Icons.Default.Warning, Color(0xFFF59E0B))
)

private fun getSampleProjects() = listOf(
    CommunityProject(1, "Community Hall Construction", "ಸಮುದಾಯ ಭವನ ನಿರ್ಮಾಣ", "New community hall for village gatherings and events", "ಗ್ರಾಮದ ಸಭೆಗಳು ಮತ್ತು ಕಾರ್ಯಕ್ರಮಗಳಿಗಾಗಿ ಹೊಸ ಸಮುದಾಯ ಭವನ", "₹45,00,000", "₹45,00,000", 72, "In Progress", "ಪ್ರಗತಿಯಲ್ಲಿ", "Public Works", "ಸಾರ್ವಜನಿಕ ಕಾಮಗಾರಿ", "Infrastructure", Color(0xFF3B82F6)),
    CommunityProject(2, "Solar Street Lights", "ಸೌರ ಬೀದಿ ದೀಪಗಳು", "Installation of 50 solar-powered street lights", "50 ಸೌರಶಕ್ತಿ ಬೀದಿ ದೀಪಗಳ ಅಳವಡಿಕೆ", "₹12,00,000", "₹12,00,000", 100, "Completed", "ಪೂರ್ಣಗೊಂಡಿದೆ", "Energy Dept.", "ಇಂಧನ ಇಲಾಖೆ", "Infrastructure", Color(0xFF10B981)),
    CommunityProject(3, "Primary School Renovation", "ಪ್ರಾಥಮಿಕ ಶಾಲೆ ನವೀಕರಣ", "Renovation of government primary school building", "ಸರ್ಕಾರಿ ಪ್ರಾಥಮಿಕ ಶಾಲೆ ಕಟ್ಟಡದ ನವೀಕರಣ", "₹28,00,000", "₹28,00,000", 45, "In Progress", "ಪ್ರಗತಿಯಲ್ಲಿ", "Education", "ಶಿಕ್ಷಣ ಇಲಾಖೆ", "Infrastructure", Color(0xFF3B82F6)),
    CommunityProject(4, "Drainage System Upgrade", "ಒಳಚರಂಡಿ ವ್ಯವಸ್ಥೆ ಉನ್ನತೀಕರಣ", "Complete overhaul of village drainage system", "ಗ್ರಾಮದ ಒಳಚರಂಡಿ ವ್ಯವಸ್ಥೆಯ ಸಂಪೂರ್ಣ ನವೀಕರಣ", "₹35,00,000", "₹35,00,000", 20, "Planning", "ಯೋಜನೆ", "Water Board", "ಜಲ ಮಂಡಳಿ", "Sanitation", Color(0xFFF59E0B))
)

val categories = listOf("All", "Infrastructure", "Water", "Roads", "Sanitation", "Safety")
val categoriesKn = listOf("ಎಲ್ಲಾ", "ಮೂಲಸೌಕರ್ಯ", "ನೀರು", "ರಸ್ತೆಗಳು", "ನೈರ್ಮಲ್ಯ", "ಸುರಕ್ಷತೆ")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityFeedScreen(
    isKannada: Boolean,
    onBack: () -> Unit,
    onReportIssue: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    
    val allIssues = remember { getSampleIssues() }
    val allProjects = remember { getSampleProjects() }

    // Filtering Logic
    val filteredIssues = remember(searchQuery, selectedCategoryIndex, allIssues) {
        allIssues.filter { issue ->
            val matchesSearch = if (searchQuery.isBlank()) true else {
                issue.titleEn.contains(searchQuery, ignoreCase = true) ||
                issue.titleKn.contains(searchQuery, ignoreCase = true) ||
                issue.descEn.contains(searchQuery, ignoreCase = true) ||
                issue.descKn.contains(searchQuery, ignoreCase = true)
            }
            val matchesCategory = if (selectedCategoryIndex == 0) true else {
                issue.category == categories[selectedCategoryIndex]
            }
            matchesSearch && matchesCategory
        }
    }

    val filteredProjects = remember(searchQuery, selectedCategoryIndex, allProjects) {
        allProjects.filter { project ->
            val matchesSearch = if (searchQuery.isBlank()) true else {
                project.titleEn.contains(searchQuery, ignoreCase = true) ||
                project.titleKn.contains(searchQuery, ignoreCase = true) ||
                project.descEn.contains(searchQuery, ignoreCase = true) ||
                project.descKn.contains(searchQuery, ignoreCase = true)
            }
            val matchesCategory = if (selectedCategoryIndex == 0) true else {
                project.category == categories[selectedCategoryIndex]
            }
            matchesSearch && matchesCategory
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isKannada) "ಸಮುದಾಯ ಫೀಡ್" else "Community Feed",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isKannada) "ವರದಿಗಳು ಮತ್ತು ಯೋಜನೆಗಳು" else "Reports & Projects",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onReportIssue,
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ) {
                Icon(Icons.Default.Add, contentDescription = "Report Issue")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text(if (isKannada) "ಹುಡುಕಿ..." else "Search...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            )

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEachIndexed { index, category ->
                    val isSelected = selectedCategoryIndex == index
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategoryIndex = index },
                        label = { Text(if (isKannada) categoriesKn[index] else category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            // Summary Stats Row (Only show if not searching/filtering)
            AnimatedVisibility(visible = searchQuery.isEmpty() && selectedCategoryIndex == 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeedStatChip(
                        label = if (isKannada) "ಒಟ್ಟು ಸಮಸ್ಯೆಗಳು" else "Issues",
                        value = "${allIssues.size}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.weight(1f)
                    )
                    FeedStatChip(
                        label = if (isKannada) "ಯೋಜನೆಗಳು" else "Projects",
                        value = "${allProjects.size}",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    FeedStatChip(
                        label = if (isKannada) "ಪರಿಹರಿಸಲಾಗಿದೆ" else "Resolved",
                        value = "${allIssues.count { it.statusEn == "Resolved" }}",
                        color = Color(0xFF10B981),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                divider = {}
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            if (isKannada) "ಸಮಸ್ಯೆಗಳು (${filteredIssues.size})" else "Issues (${filteredIssues.size})",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    icon = { Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            if (isKannada) "ಯೋಜನೆಗಳು (${filteredProjects.size})" else "Projects (${filteredProjects.size})",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    icon = { Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // Content
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> {
                        if (filteredIssues.isEmpty()) {
                            EmptyState(isKannada, "ಸಮಸ್ಯೆಗಳು ಕಂಡುಬಂದಿಲ್ಲ", "No Issues Found")
                        } else {
                            IssuesList(filteredIssues, isKannada)
                        }
                    }
                    1 -> {
                        if (filteredProjects.isEmpty()) {
                            EmptyState(isKannada, "ಯೋಜನೆಗಳು ಕಂಡುಬಂದಿಲ್ಲ", "No Projects Found")
                        } else {
                            ProjectsList(filteredProjects, isKannada)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(isKannada: Boolean, textKn: String, textEn: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (isKannada) textKn else textEn,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = if (isKannada) "ದಯವಿಟ್ಟು ನಿಮ್ಮ ಹುಡುಕಾಟ ಅಥವಾ ಫಿಲ್ಟರ್ ಅನ್ನು ಹೊಂದಿಸಿ" else "Please adjust your search or filter",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun FeedStatChip(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color.copy(alpha = 0.8f),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun IssuesList(issues: List<CommunityIssue>, isKannada: Boolean) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(issues, key = { _, issue -> issue.id }) { index, issue ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                visible = true
            }
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(durationMillis = 300, delayMillis = index * 50)
                ) + fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = index * 50))
            ) {
                IssueCard(issue, isKannada)
            }
        }
    }
}

@Composable
private fun IssueCard(issue: CommunityIssue, isKannada: Boolean) {
    var upvoted by remember { mutableStateOf(false) }
    val currentUpvotes = if (upvoted) issue.upvotes + 1 else issue.upvotes

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Reporter + Time + Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = issue.reporterEn.first().toString(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isKannada) issue.reporterKn else issue.reporterEn,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = issue.timeAgo,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = issue.statusColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = if (isKannada) issue.statusKn else issue.statusEn,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = issue.statusColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = if (isKannada) issue.titleKn else issue.titleEn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isKannada) issue.descKn else issue.descEn,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Location + Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isKannada) issue.locationKn else issue.locationEn,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = issue.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))

            // Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Upvote
                val upvoteColor by animateColorAsState(
                    targetValue = if (upvoted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    label = "upvote"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { upvoted = !upvoted }.padding(8.dp)
                ) {
                    Icon(
                        Icons.Default.ThumbUp,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = upvoteColor
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "$currentUpvotes",
                        style = MaterialTheme.typography.labelLarge,
                        color = upvoteColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Comments
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {  }.padding(8.dp)
                ) {
                    Icon(
                        Icons.Default.MailOutline,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${issue.comments}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Share
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {  }.padding(8.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isKannada) "ಹಂಚಿಕೊಳ್ಳಿ" else "Share", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun ProjectsList(projects: List<CommunityProject>, isKannada: Boolean) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(projects, key = { _, project -> project.id }) { index, project ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                visible = true
            }
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(durationMillis = 300, delayMillis = index * 50)
                ) + fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = index * 50))
            ) {
                CommunityProjectCard(project, isKannada)
            }
        }
    }
}

@Composable
private fun CommunityProjectCard(project: CommunityProject, isKannada: Boolean) {
    var startAnim by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnim = true }
    val animProgress by animateFloatAsState(
        targetValue = if (startAnim) project.progress / 100f else 0f,
        animationSpec = tween(1000, 200),
        label = "prog"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = project.statusColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = if (isKannada) project.statusKn else project.statusEn,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = project.statusColor
                    )
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = if (isKannada) project.departmentKn else project.departmentEn,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isKannada) project.titleKn else project.titleEn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isKannada) project.descKn else project.descEn,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Budget
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isKannada) "ಬಜೆಟ್" else "Budget",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = if (isKannada) project.budgetKn else project.budgetEn,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress
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
                    color = project.statusColor
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { animProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = project.statusColor,
                trackColor = project.statusColor.copy(alpha = 0.15f)
            )
        }
    }
}
