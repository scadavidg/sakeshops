package org.example.project

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ComposeAppCommonTest {
    
    @Test
    fun `app should load and display sake shops`() = runTest {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val getAllSakeShopsUseCase = org.example.project.domain.usecase.GetAllSakeShopsUseCase(repository)
        val getSakeShopDetailUseCase = org.example.project.domain.usecase.GetSakeShopDetailUseCase(repository)
        
        // When
        val shopsResult = getAllSakeShopsUseCase()
        
        // Then
        when (shopsResult) {
            is org.example.project.domain.model.Result.Success -> {
                val shops = shopsResult.data
                assertTrue(shops.isNotEmpty(), "Should have shops")
                assertEquals(8, shops.size, "Should have 8 shops")
                
                // Verify first shop has all required fields
                val firstShop = shops.first()
                assertNotNull(firstShop.id, "Shop should have ID")
                assertNotNull(firstShop.name, "Shop should have name")
                assertNotNull(firstShop.address, "Shop should have address")
                assertTrue(firstShop.rating > 0.0, "Shop should have positive rating")
            }
            is org.example.project.domain.model.Result.Failure -> {
                assertTrue(false, "Should not fail: ${shopsResult.error.message}")
            }
            is org.example.project.domain.model.Result.Loading -> {
                assertTrue(false, "Should not be loading")
            }
        }
    }
    
    @Test
    fun `app should load shop details`() = runTest {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val getSakeShopDetailUseCase = org.example.project.domain.usecase.GetSakeShopDetailUseCase(repository)
        
        // When
        val detailResult = getSakeShopDetailUseCase("1")
        
        // Then
        when (detailResult) {
            is org.example.project.domain.model.Result.Success -> {
                val shop = detailResult.data
                assertNotNull(shop.id, "Shop detail should have ID")
                assertNotNull(shop.name, "Shop detail should have name")
                assertNotNull(shop.address, "Shop detail should have address")
                assertNotNull(shop.description, "Shop detail should have description")
                assertNotNull(shop.imageUrl, "Shop detail should have image URL")
                assertNotNull(shop.websiteUrl, "Shop detail should have website URL")
                assertTrue(shop.rating > 0.0, "Shop detail should have positive rating")
            }
            is org.example.project.domain.model.Result.Failure -> {
                assertTrue(false, "Should not fail: ${detailResult.error.message}")
            }
            is org.example.project.domain.model.Result.Loading -> {
                assertTrue(false, "Should not be loading")
            }
        }
    }
    
    @Test
    fun `app should handle invalid shop ID gracefully`() = runTest {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val getSakeShopDetailUseCase = org.example.project.domain.usecase.GetSakeShopDetailUseCase(repository)
        
        // When
        val detailResult = getSakeShopDetailUseCase("invalid_id")
        
        // Then
        when (detailResult) {
            is org.example.project.domain.model.Result.Success -> {
                assertTrue(false, "Should not succeed for invalid ID")
            }
            is org.example.project.domain.model.Result.Failure -> {
                // Expected behavior
                assertTrue(true, "Should fail gracefully for invalid ID")
            }
            is org.example.project.domain.model.Result.Loading -> {
                assertTrue(false, "Should not be loading")
            }
        }
    }
}
