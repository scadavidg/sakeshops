package org.example.project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShopDto(
    val id: String,
    val name: String,
    val address: String,
    val rating: Double,
    val imageUrl: String,
    val description: String,
    val websiteUrl: String
)

@Serializable
data class SakeShopsResponse(
    val shops: List<ShopDto>
)
