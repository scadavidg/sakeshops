package org.example.project.domain.model

data class SakeShopDetail(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val rating: Float,
    val address: String,
    val websiteUrl: String
)
