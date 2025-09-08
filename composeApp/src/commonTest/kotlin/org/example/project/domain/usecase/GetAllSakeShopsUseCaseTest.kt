package org.example.project.domain.usecase

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetAllSakeShopsUseCaseTest {
    
    private val useCase = GetAllSakeShopsUseCase(
        org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
    )
    
    @Test
    fun `invoke should return all shops`() = runTest {
        // When
        val result = useCase()
        
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
    fun `invoke should return shops with valid data`() = runTest {
        // When
        val result = useCase()
        
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
    fun `multiple invocations should return same data`() = runTest {
        // When
        val result1 = useCase()
        val result2 = useCase()
        
        // Then
        when (result1) {
            is org.example.project.domain.model.Result.Success -> {
                when (result2) {
                    is org.example.project.domain.model.Result.Success -> {
                        assertEquals(result1.data.size, result2.data.size)
                        assertEquals(result1.data.map { it.id }, result2.data.map { it.id })
                    }
                    else -> assertTrue(false, "Second call should also succeed")
                }
            }
            else -> assertTrue(false, "First call should succeed")
        }
    }
}
