package org.example.project.data.repository

import kotlinx.coroutines.test.runTest
import org.example.project.data.model.ShopDto
import org.example.project.data.source.local.ShopLocalDataSource
import org.example.project.domain.model.Result
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SakeShopRepositoryImplTest {

    private val mockLocalDataSource = object : ShopLocalDataSource {
        private val mockShops = listOf(
            ShopDto(
                id = "1",
                name = "Test Sake Shop 1",
                address = "Test Address 1",
                rating = 4.5,
                imageUrl = "https://example.com/image1.jpg",
                description = "Test Description 1",
                websiteUrl = "https://example.com/shop1"
            ),
            ShopDto(
                id = "2",
                name = "Test Sake Shop 2",
                address = "Test Address 2",
                rating = 4.0,
                imageUrl = "https://example.com/image2.jpg",
                description = "Test Description 2",
                websiteUrl = "https://example.com/shop2"
            )
        )

        override suspend fun getAllShops(): List<ShopDto> = mockShops

        override suspend fun getShopById(id: String): ShopDto? = mockShops.find { it.id == id }

        override suspend fun initializeData(): Boolean = true
    }

    private val repository = SakeShopRepositoryImpl(mockLocalDataSource)

    @Test
    fun `should return success with list of sake shop previews when local data source returns data`() = runTest {
        // When
        val result = repository.getAllSakeShops()

        // Then
        assertTrue(result is Result.Success)
        val shops = (result as Result.Success).data
        assertEquals(2, shops.size)
        assertEquals("Test Sake Shop 1", shops[0].name)
        assertEquals("Test Address 1", shops[0].address)
        assertEquals(4.5f, shops[0].rating)
        assertEquals("1", shops[0].id)
        assertEquals("Test Sake Shop 2", shops[1].name)
        assertEquals("Test Address 2", shops[1].address)
        assertEquals(4.0f, shops[1].rating)
        assertEquals("2", shops[1].id)
    }

    @Test
    fun `should return success with sake shop detail when local data source returns data`() = runTest {
        // When
        val result = repository.getSakeShopDetail("1")

        // Then
        assertTrue(result is Result.Success)
        val shop = (result as Result.Success).data
        assertEquals("1", shop.id)
        assertEquals("Test Sake Shop 1", shop.name)
        assertEquals("https://example.com/image1.jpg", shop.imageUrl)
        assertEquals("Test Description 1", shop.description)
        assertEquals(4.5f, shop.rating)
        assertEquals("Test Address 1", shop.address)
        assertEquals("https://example.com/shop1", shop.websiteUrl)
    }

    @Test
    fun `should return failure when shop not found`() = runTest {
        // When
        val result = repository.getSakeShopDetail("999")

        // Then
        assertTrue(result is Result.Failure)
    }

    @Test
    fun `should return success when initializing data`() = runTest {
        // When
        val result = repository.initializeData()

        // Then
        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data)
    }
}
