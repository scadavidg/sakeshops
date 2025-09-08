package org.example.project.data.model

import kotlin.test.Test
import kotlin.test.assertEquals

class MappersTest {

    private val testShopDto = ShopDto(
        id = "1",
        name = "Test Sake Shop",
        address = "Test Address",
        rating = 4.5,
        imageUrl = "https://example.com/image.jpg",
        description = "Test Description",
        websiteUrl = "https://example.com/shop"
    )

    @Test
    fun `should map ShopDto to SakeShopPreview correctly`() {
        // When
        val result = testShopDto.toSakeShopPreview()

        // Then
        assertEquals("1", result.id)
        assertEquals("Test Sake Shop", result.name)
        assertEquals("Test Address", result.address)
        assertEquals(4.5f, result.rating)
    }

    @Test
    fun `should map ShopDto to SakeShopDetail correctly`() {
        // When
        val result = testShopDto.toSakeShopDetail()

        // Then
        assertEquals("1", result.id)
        assertEquals("Test Sake Shop", result.name)
        assertEquals("https://example.com/image.jpg", result.imageUrl)
        assertEquals("Test Description", result.description)
        assertEquals(4.5f, result.rating)
        assertEquals("Test Address", result.address)
        assertEquals("https://example.com/shop", result.websiteUrl)
    }

    @Test
    fun `should convert rating from Double to Float correctly`() {
        // Given
        val shopDtoWithDecimalRating = testShopDto.copy(rating = 4.75)

        // When
        val preview = shopDtoWithDecimalRating.toSakeShopPreview()
        val detail = shopDtoWithDecimalRating.toSakeShopDetail()

        // Then
        assertEquals(4.75f, preview.rating)
        assertEquals(4.75f, detail.rating)
    }
}
