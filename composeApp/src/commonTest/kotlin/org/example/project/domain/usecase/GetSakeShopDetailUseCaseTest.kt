package org.example.project.domain.usecase

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetSakeShopDetailUseCaseTest {
    
    private val useCase = GetSakeShopDetailUseCase(
        org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
    )
    
    @Test
    fun `invoke should return correct shop for valid ID`() = runTest {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val allShopsResult = repository.getAllSakeShops()
        val firstShop = when (allShopsResult) {
            is org.example.project.domain.model.Result.Success -> allShopsResult.data.first()
            else -> return@runTest
        }
        
        // When
        val result = useCase(firstShop.id)
        
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
    fun `invoke should return failure for invalid ID`() = runTest {
        // When
        val result = useCase("invalid_id")
        
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
    
    @Test
    fun `invoke should return failure for empty ID`() = runTest {
        // When
        val result = useCase("")
        
        // Then
        when (result) {
            is org.example.project.domain.model.Result.Success -> {
                assertTrue(false, "Should not succeed for empty ID")
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
