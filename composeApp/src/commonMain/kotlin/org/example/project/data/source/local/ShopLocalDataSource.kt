package org.example.project.data.source.local

import org.example.project.data.model.ShopDto

interface ShopLocalDataSource {
    suspend fun getAllShops(): List<ShopDto>
    suspend fun getShopById(id: String): ShopDto?
}
