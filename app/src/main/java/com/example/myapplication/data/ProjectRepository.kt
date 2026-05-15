package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.model.Project
import kotlinx.serialization.json.Json

class ProjectRepository(private val context: Context) {
    private val json = Json { ignoreUnknownKeys = true }
    private var cachedProjects: List<Project>? = null

    suspend fun getProjects(forceRefresh: Boolean = false): List<Project> {
        if (forceRefresh) {
            cachedProjects = null
        }
        cachedProjects?.let { return it }

        val jsonString = context.assets
            .open("projects.json")
            .bufferedReader()
            .use { it.readText() }
        return json.decodeFromString<List<Project>>(jsonString).also {
            cachedProjects = it
        }
    }
}
