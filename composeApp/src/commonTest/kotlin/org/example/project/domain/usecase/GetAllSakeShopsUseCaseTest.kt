package org.example.project.domain.usecase

import kotlinx.coroutines.test.runTest
import org.example.project.domain.model.Result
import org.example.project.domain.model.SakeShopPreview
import org.example.project.domain.model.SakeShopDetail
import org.example.project.domain.repository.SakeShopRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAllSakeShopsUseCaseTest {

    private val mockRepository = object : SakeShopRepository {
        override suspend fun getAllSakeShops(): Result<List<SakeShopPreview>> {
            return Result.Success(listOf(
                SakeShopPreview(
                    id = "1",
                    name = "Test Sake Shop 1",
                    address = "Test Address 1",
                    rating = 4.5f
                ),
                SakeShopPreview(
                    id = "2",
                    name = "Test Sake Shop 2",
                    address = "Test Address 2",
                    rating = 4.0f
                )
            ))
        }

        override suspend fun getSakeShopDetail(id: String): Result<SakeShopDetail> {
            return Result.Failure(Exception("Not implemented"))
        }
    }

    private val useCase = GetAllSakeShopsUseCase(mockRepository)

    @Test
    fun `should return success with list of sake shops when repository returns data`() = runTest {
        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        val shops = (result as Result.Success).data
        assertEquals(2, shops.size)
        assertEquals("Test Sake Shop 1", shops[0].name)
        assertEquals("Test Address 1", shops[0].address)
        assertEquals(4.5f, shops[0].rating)
        assertEquals("Test Sake Shop 2", shops[1].name)
        assertEquals("Test Address 2", shops[1].address)
        assertEquals(4.0f, shops[1].rating)
    }

    @Test
    fun `should return success with empty list when repository returns empty list`() = runTest {
        // Given
        val emptyRepository = object : SakeShopRepository {
            override suspend fun getAllSakeShops(): Result<List<SakeShopPreview>> = Result.Success(emptyList())
            override suspend fun getSakeShopDetail(id: String): Result<SakeShopDetail> = Result.Failure(Exception("Not implemented"))
        }
        val useCase = GetAllSakeShopsUseCase(emptyRepository)

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data.isEmpty())
    }
}
