package org.example.project.data.repository

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SakeShopRepositoryImplTest {
    
    private val repository = SakeShopRepositoryImpl(org.example.project.data.source.local.ShopLocalDataSourceImpl())
    
    @Test
    fun `getAllSakeShops should return all shops`() = runTest {
        // When
        val result = repository.getAllSakeShops()
        
        // Then
        when (result) {
            is org.example.project.domain.model.Result.Success -> {
                assertEquals(8, result.data.size)
            }
            is org.example.project.domain.model.Result.Failure -> {
                assertTrue(false, "Should not fail: ${result.error.message}")
            }
            is org.example.project.domain.model.Result.Loading -> {
                assertTrue(false, "Should not be loading")
            }
        }
    }
    
    @Test
    fun `getAllSakeShops should return shops with valid data`() = runTest {
        // When
        val result = repository.getAllSakeShops()
        
        // Then
        when (result) {
            is org.example.project.domain.model.Result.Success -> {
                val shops = result.data
                assertTrue(shops.isNotEmpty())
                shops.forEach { shop ->
                    assertTrue(shop.id.isNotBlank(), "Shop ID should not be blank")
                    assertTrue(shop.name.isNotBlank(), "Shop name should not be blank")
                    assertTrue(shop.address.isNotBlank(), "Shop address should not be blank")
                    assertTrue(shop.rating >= 0.0, "Shop rating should be non-negative")
                }
            }
            else -> assertTrue(false, "Should return success")
        }
    }
    
    @Test
    fun `getSakeShopDetail should return correct shop for valid ID`() = runTest {
        // Given
        val allShopsResult = repository.getAllSakeShops()
        val firstShop = when (allShopsResult) {
            is org.example.project.domain.model.Result.Success -> allShopsResult.data.first()
            else -> return@runTest
        }
        
        // When
        val result = repository.getSakeShopDetail(firstShop.id)
        
        // Then
        when (result) {
            is org.example.project.domain.model.Result.Success -> {
                val shop = result.data
                assertEquals(firstShop.id, shop.id)
                assertEquals(firstShop.name, shop.name)
                assertEquals(firstShop.address, shop.address)
                assertEquals(firstShop.rating, shop.rating)
                assertNotNull(shop.description)
                assertNotNull(shop.imageUrl)
                assertNotNull(shop.websiteUrl)
            }
            is org.example.project.domain.model.Result.Failure -> {
                assertTrue(false, "Should not fail: ${result.error.message}")
            }
            is org.example.project.domain.model.Result.Loading -> {
                assertTrue(false, "Should not be loading")
            }
        }
    }
    
    @Test
    fun `getSakeShopDetail should return failure for invalid ID`() = runTest {
        // When
        val result = repository.getSakeShopDetail("invalid_id")
        
        // Then
        when (result) {
            is org.example.project.domain.model.Result.Success -> {
                assertTrue(false, "Should not succeed for invalid ID")
            }
            is org.example.project.domain.model.Result.Failure -> {
                // Expected behavior
                assertTrue(true)
            }
            is org.example.project.domain.model.Result.Loading -> {
                assertTrue(false, "Should not be loading")
            }
        }
    }
}
