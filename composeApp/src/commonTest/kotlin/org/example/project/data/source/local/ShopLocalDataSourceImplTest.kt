package org.example.project.data.source.local

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ShopLocalDataSourceImplTest {
    
    private val dataSource = ShopLocalDataSourceImpl()
    
    @Test
    fun `getAllShops should return all shops`() = runTest {
        // When
        val shops = dataSource.getAllShops()
        
        // Then
        assertNotNull(shops)
        assertEquals(8, shops.size)
    }
    
    @Test
    fun `getAllShops should return shops with valid data`() = runTest {
        // When
        val shops = dataSource.getAllShops()
        
        // Then
        assertTrue(shops.isNotEmpty())
        shops.forEach { shop ->
            assertTrue(shop.id.isNotBlank(), "Shop ID should not be blank")
            assertTrue(shop.name.isNotBlank(), "Shop name should not be blank")
            assertTrue(shop.address.isNotBlank(), "Shop address should not be blank")
            assertTrue(shop.description.isNotBlank(), "Shop description should not be blank")
            assertTrue(shop.imageUrl.isNotBlank(), "Shop imageUrl should not be blank")
            assertTrue(shop.websiteUrl.isNotBlank(), "Shop websiteUrl should not be blank")
            assertTrue(shop.rating >= 0.0, "Shop rating should be non-negative")
        }
    }
    
    @Test
    fun `getShopById should return correct shop for valid ID`() = runTest {
        // Given
        val shops = dataSource.getAllShops()
        val firstShop = shops.first()
        
        // When
        val foundShop = dataSource.getShopById(firstShop.id)
        
        // Then
        assertNotNull(foundShop)
        assertEquals(firstShop.id, foundShop.id)
        assertEquals(firstShop.name, foundShop.name)
        assertEquals(firstShop.address, foundShop.address)
        assertEquals(firstShop.rating, foundShop.rating)
    }
    
    @Test
    fun `getShopById should return null for invalid ID`() = runTest {
        // When
        val foundShop = dataSource.getShopById("invalid_id")
        
        // Then
        assertEquals(null, foundShop)
    }
    
    @Test
    fun `getShopById should return null for empty ID`() = runTest {
        // When
        val foundShop = dataSource.getShopById("")
        
        // Then
        assertEquals(null, foundShop)
    }
    
    @Test
    fun `multiple calls to getAllShops should return same data`() = runTest {
        // When
        val shops1 = dataSource.getAllShops()
        val shops2 = dataSource.getAllShops()
        
        // Then
        assertEquals(shops1.size, shops2.size)
        assertEquals(shops1.map { it.id }, shops2.map { it.id })
    }
}
