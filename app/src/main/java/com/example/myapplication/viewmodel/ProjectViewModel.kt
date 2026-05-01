package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ProjectRepository
import com.example.myapplication.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _isKannada = MutableStateFlow(false)
    val isKannada: StateFlow<Boolean> = _isKannada.asStateFlow()

    init {
        loadProjects()
    }

    private fun loadProjects() {
        _projects.value = repository.getProjects()
    }

    fun toggleLanguage() {
        _isKannada.value = !_isKannada.value
    }
}
