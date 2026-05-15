package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProjectRepository
import com.example.myapplication.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ProjectFilter { ALL, COMPLETED, IN_PROGRESS, NOT_STARTED }
enum class ProjectSort { DEFAULT, PROGRESS_DESC, PROGRESS_ASC }

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _allProjects = MutableStateFlow<List<Project>>(emptyList())
    val allProjects: StateFlow<List<Project>> = _allProjects.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _currentFilter = MutableStateFlow(ProjectFilter.ALL)
    val currentFilter: StateFlow<ProjectFilter> = _currentFilter.asStateFlow()

    private val _currentSort = MutableStateFlow(ProjectSort.DEFAULT)
    val currentSort: StateFlow<ProjectSort> = _currentSort.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _isKannada = MutableStateFlow(false)
    val isKannada: StateFlow<Boolean> = _isKannada.asStateFlow()

    init {
        loadProjects()
    }

    fun refreshProjects() {
        loadProjects(forceRefresh = true)
    }

    private fun loadProjects(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            runCatching {
                repository.getProjects(forceRefresh = forceRefresh)
            }.onSuccess { projectList ->
                _allProjects.value = projectList
                applyFiltersAndSort()
            }.onFailure {
                _allProjects.value = emptyList()
                _projects.value = emptyList()
                _errorMessage.value = it.localizedMessage ?: "Unable to load projects."
            }

            _isLoading.value = false
        }
    }

    fun toggleLanguage() {
        _isKannada.value = !_isKannada.value
    }

    fun setFilter(filter: ProjectFilter) {
        _currentFilter.value = filter
        applyFiltersAndSort()
    }

    fun setSort(sort: ProjectSort) {
        _currentSort.value = sort
        applyFiltersAndSort()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        applyFiltersAndSort()
    }

    private fun applyFiltersAndSort() {
        var filteredList = _allProjects.value

        val trimmedQuery = _searchQuery.value.trim()
        if (trimmedQuery.isNotEmpty()) {
            filteredList = filteredList.filter { project ->
                listOf(
                    project.titleEn,
                    project.titleKn,
                    project.descriptionEn,
                    project.descriptionKn,
                    project.statusEn,
                    project.statusKn,
                    project.budget,
                    project.expectedCompletion
                ).any { it.contains(trimmedQuery, ignoreCase = true) }
            }
        }

        // Apply Filter
        filteredList = when (_currentFilter.value) {
            ProjectFilter.ALL -> filteredList
            ProjectFilter.COMPLETED -> filteredList.filter { it.progress == 100 }
            ProjectFilter.IN_PROGRESS -> filteredList.filter { it.progress > 0 && it.progress < 100 }
            ProjectFilter.NOT_STARTED -> filteredList.filter { it.progress == 0 }
        }

        // Apply Sort
        filteredList = when (_currentSort.value) {
            ProjectSort.DEFAULT -> filteredList
            ProjectSort.PROGRESS_DESC -> filteredList.sortedByDescending { it.progress }
            ProjectSort.PROGRESS_ASC -> filteredList.sortedBy { it.progress }
        }

        _projects.value = filteredList
    }
}
