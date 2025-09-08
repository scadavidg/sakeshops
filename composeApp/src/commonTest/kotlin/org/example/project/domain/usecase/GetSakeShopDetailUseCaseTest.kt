package org.example.project.domain.usecase

import kotlinx.coroutines.test.runTest
import org.example.project.domain.model.Result
import org.example.project.domain.model.SakeShopDetail
import org.example.project.domain.model.SakeShopPreview
import org.example.project.domain.repository.SakeShopRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetSakeShopDetailUseCaseTest {

    private val mockRepository = object : SakeShopRepository {
        override suspend fun getAllSakeShops(): Result<List<SakeShopPreview>> = Result.Success(emptyList())

        override suspend fun getSakeShopDetail(id: String): Result<SakeShopDetail> {
            return if (id == "1") {
                Result.Success(SakeShopDetail(
                    id = "1",
                    name = "Test Sake Shop",
                    imageUrl = "https://example.com/image.jpg",
                    description = "A great sake shop",
                    rating = 4.5f,
                    address = "Test Address",
                    websiteUrl = "https://example.com"
                ))
            } else {
                Result.Failure(Exception("Shop with id $id not found"))
            }
        }
    }

    private val useCase = GetSakeShopDetailUseCase(mockRepository)

    @Test
    fun `should return success with sake shop detail when repository returns data`() = runTest {
        // When
        val result = useCase("1")

        // Then
        assertTrue(result is Result.Success)
        val shop = (result as Result.Success).data
        assertEquals("1", shop.id)
        assertEquals("Test Sake Shop", shop.name)
        assertEquals("https://example.com/image.jpg", shop.imageUrl)
        assertEquals("A great sake shop", shop.description)
        assertEquals(4.5f, shop.rating)
        assertEquals("Test Address", shop.address)
        assertEquals("https://example.com", shop.websiteUrl)
    }

    @Test
    fun `should return failure when repository returns failure`() = runTest {
        // When
        val result = useCase("2")

        // Then
        assertTrue(result is Result.Failure)
    }
}
