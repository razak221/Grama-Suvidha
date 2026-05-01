package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.model.Project
import kotlinx.serialization.json.Json
import java.io.IOException

class ProjectRepository(private val context: Context) {
    private val json = Json { ignoreUnknownKeys = true }

    fun getProjects(): List<Project> {
        return try {
            val jsonString = context.assets.open("projects.json").bufferedReader().use { it.readText() }
            json.decodeFromString(jsonString)
        } catch (e: IOException) {
            emptyList()
        }
    }
}
