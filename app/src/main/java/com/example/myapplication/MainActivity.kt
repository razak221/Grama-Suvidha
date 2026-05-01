package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.ProjectRepository
import com.example.myapplication.ui.ProjectDetailScreen
import com.example.myapplication.ui.ProjectListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.ProjectViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val repository = ProjectRepository(applicationContext)
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
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

@Composable
fun GramaSuvidhaApp(viewModel: ProjectViewModel) {
    val navController = rememberNavController()
    val projects by viewModel.projects.collectAsState()
    val isKannada by viewModel.isKannada.collectAsState()

    NavHost(
        navController = navController, 
        startDestination = "projectList",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        }
    ) {
        composable("projectList") {
            ProjectListScreen(
                projects = projects,
                isKannada = isKannada,
                onProjectClick = { project ->
                    navController.navigate("projectDetail/${project.id}")
                },
                onToggleLanguage = { viewModel.toggleLanguage() }
            )
        }
        composable("projectDetail/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")?.toIntOrNull()
            val project = projects.find { it.id == projectId }
            project?.let {
                ProjectDetailScreen(
                    project = it,
                    isKannada = isKannada,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
