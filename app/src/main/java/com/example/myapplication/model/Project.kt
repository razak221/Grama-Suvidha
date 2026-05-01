package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val id: Int,
    val titleEn: String,
    val titleKn: String,
    val budget: String,
    val expectedCompletion: String,
    val statusEn: String,
    val statusKn: String,
    val progress: Int,
    val descriptionEn: String,
    val descriptionKn: String,
    val beforeImageUrl: String,
    val afterImageUrl: String? = null
)
