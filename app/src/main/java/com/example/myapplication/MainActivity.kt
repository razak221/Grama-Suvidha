package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.ProjectRepository
import com.example.myapplication.model.Project
import com.example.myapplication.ui.CommunityFeedScreen
import com.example.myapplication.ui.DashboardScreen
import com.example.myapplication.ui.HelpSupportScreen
import com.example.myapplication.ui.LoginScreen
import com.example.myapplication.ui.NewProjectScreen
import com.example.myapplication.ui.ProfileScreen
import com.example.myapplication.ui.ProjectDetailScreen
import com.example.myapplication.ui.ProjectListScreen
import com.example.myapplication.ui.ReportIssueScreen
import com.example.myapplication.ui.SettingsScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.ProjectFilter
import com.example.myapplication.viewmodel.ProjectSort
import com.example.myapplication.viewmodel.ProjectViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = ProjectRepository(applicationContext)
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ProjectViewModel(repository) as T
            }
        }

        setContent {
            MyApplicationTheme {
                val viewModel: ProjectViewModel = viewModel(factory = viewModelFactory)
                GramaSuvidhaApp(viewModel)
            }
        }
    }
}

private data class TopLevelDestination(
    val route: String,
    val labelEn: String,
    val labelKn: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private object Routes {
    const val Login = "login"
    const val Dashboard = "dashboard"
    const val ProjectList = "projectList"
    const val ProjectDetail = "projectDetail"
    const val Profile = "profile"
    const val Settings = "settings"
    const val Help = "help"
    const val ReportIssue = "reportIssue"
    const val NewProject = "newProject"
    const val CommunityFeed = "communityFeed"
}

@Composable
fun GramaSuvidhaApp(viewModel: ProjectViewModel) {
    val navController = rememberNavController()
    val projects by viewModel.projects.collectAsState()
    val allProjects by viewModel.allProjects.collectAsState()
    val isKannada by viewModel.isKannada.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentFilter by viewModel.currentFilter.collectAsState()
    val currentSort by viewModel.currentSort.collectAsState()

    val topLevelDestinations = listOf(
        TopLevelDestination(Routes.Dashboard, "Dashboard", "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್", Icons.Default.Home),
        TopLevelDestination(Routes.ProjectList, "Projects", "ಯೋಜನೆಗಳು", Icons.AutoMirrored.Filled.List),
        TopLevelDestination(Routes.Profile, "Profile", "ಪ್ರೊಫೈಲ್", Icons.Default.Person)
    )

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottomBar = topLevelDestinations.any { destination ->
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            }

            if (showBottomBar) {
                NavigationBar {
                    topLevelDestinations.forEach { destination ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = if (isKannada) destination.labelKn else destination.labelEn
                                )
                            },
                            label = { Text(if (isKannada) destination.labelKn else destination.labelEn) },
                            selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.Login,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250)
                ) + fadeOut(animationSpec = tween(250))
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250)
                ) + fadeOut(animationSpec = tween(250))
            }
        ) {
            composable(Routes.Login) {
                LoginScreen(
                    isKannada = isKannada,
                    onToggleLanguage = viewModel::toggleLanguage,
                    onLoginSuccess = {
                        navController.navigate(Routes.Dashboard) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.Dashboard) {
                DashboardScreen(
                    projects = allProjects,
                    isKannada = isKannada,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onRetry = viewModel::refreshProjects,
                    onReportIssue = { navController.navigate(Routes.ReportIssue) },
                    onNewProject = { navController.navigate(Routes.NewProject) },
                    onCommunityFeed = { navController.navigate(Routes.CommunityFeed) }
                )
            }
            composable(Routes.ProjectList) {
                ProjectListScreen(
                    allProjectsCount = allProjects.size,
                    projects = projects,
                    isKannada = isKannada,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    searchQuery = searchQuery,
                    currentFilter = currentFilter,
                    currentSort = currentSort,
                    onProjectClick = { project: Project ->
                        navController.navigate("${Routes.ProjectDetail}/${project.id}")
                    },
                    onToggleLanguage = viewModel::toggleLanguage,
                    onSearchChange = viewModel::setSearchQuery,
                    onFilterChange = viewModel::setFilter,
                    onSortChange = viewModel::setSort,
                    onRetry = viewModel::refreshProjects
                )
            }
            composable("${Routes.ProjectDetail}/{projectId}") { backStackEntry ->
                val projectId = backStackEntry.arguments?.getString("projectId")?.toIntOrNull()
                val project = allProjects.find { it.id == projectId }
                project?.let {
                    ProjectDetailScreen(
                        project = it,
                        isKannada = isKannada,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            composable(Routes.Profile) {
                ProfileScreen(
                    isKannada = isKannada,
                    onToggleLanguage = viewModel::toggleLanguage,
                    onNavigateToSettings = { navController.navigate(Routes.Settings) },
                    onNavigateToHelp = { navController.navigate(Routes.Help) },
                    onLogout = {
                        navController.navigate(Routes.Login) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.Settings) {
                SettingsScreen(
                    isKannada = isKannada,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.Help) {
                HelpSupportScreen(
                    isKannada = isKannada,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.ReportIssue) {
                ReportIssueScreen(
                    isKannada = isKannada,
                    onBack = { navController.popBackStack() },
                    onSubmit = { navController.popBackStack() }
                )
            }
            composable(Routes.NewProject) {
                NewProjectScreen(
                    isKannada = isKannada,
                    onBack = { navController.popBackStack() },
                    onSubmit = { navController.popBackStack() }
                )
            }
            composable(Routes.CommunityFeed) {
                CommunityFeedScreen(
                    isKannada = isKannada,
                    onBack = { navController.popBackStack() },
                    onReportIssue = { navController.navigate(Routes.ReportIssue) }
                )
            }
        }
    }
}
