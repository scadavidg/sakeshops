package org.example.project.data.source.local

import org.example.project.data.model.ShopDto

class ShopLocalDataSourceImpl : ShopLocalDataSource {
    
    override suspend fun getAllShops(): List<ShopDto> {
        return try {
            val response = JsonLoader.loadSakeShops()
            response.shops
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    override suspend fun getShopById(id: String): ShopDto? {
        return try {
            val response = JsonLoader.loadSakeShops()
            val shop = response.shops.find { it.id == id }
            shop
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
