package org.example.project.data.source.local

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class JsonLoaderTest {
    
    @Test
    fun `loadSakeShops should return valid data with 8 shops`() {
        // When
        val result = JsonLoader.loadSakeShops()
        
        // Then
        assertNotNull(result)
        assertEquals(8, result.shops.size)
    }
    
    @Test
    fun `loadSakeShops should return shops with valid data structure`() {
        // When
        val result = JsonLoader.loadSakeShops()
        
        // Then
        val shops = result.shops
        assertTrue(shops.isNotEmpty())
        
        // Check first shop
        val firstShop = shops.first()
        assertNotNull(firstShop.id)
        assertNotNull(firstShop.name)
        assertNotNull(firstShop.address)
        assertNotNull(firstShop.description)
        assertNotNull(firstShop.imageUrl)
        assertNotNull(firstShop.websiteUrl)
        assertTrue(firstShop.rating > 0.0)
    }
    
    @Test
    fun `loadSakeShops should return shops with unique IDs`() {
        // When
        val result = JsonLoader.loadSakeShops()
        
        // Then
        val shops = result.shops
        val ids = shops.map { it.id }
        val uniqueIds = ids.distinct()
        assertEquals(ids.size, uniqueIds.size, "All shop IDs should be unique")
    }
    
    @Test
    fun `loadSakeShops should return shops with valid ratings`() {
        // When
        val result = JsonLoader.loadSakeShops()
        
        // Then
        val shops = result.shops
        shops.forEach { shop ->
            assertTrue(shop.rating >= 0.0, "Rating should be non-negative")
            assertTrue(shop.rating <= 5.0, "Rating should not exceed 5.0")
        }
    }
    
    @Test
    fun `loadSakeShops should return shops with non-empty names`() {
        // When
        val result = JsonLoader.loadSakeShops()
        
        // Then
        val shops = result.shops
        shops.forEach { shop ->
            assertTrue(shop.name.isNotBlank(), "Shop name should not be blank")
        }
    }
}
