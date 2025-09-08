package org.example.project.data.repository

import org.example.project.data.model.toSakeShopDetail
import org.example.project.data.model.toSakeShopPreview
import org.example.project.data.source.local.ShopLocalDataSource
import org.example.project.domain.model.Result
import org.example.project.domain.model.SakeShopDetail
import org.example.project.domain.model.SakeShopPreview
import org.example.project.domain.repository.SakeShopRepository

class SakeShopRepositoryImpl(
    private val localDataSource: ShopLocalDataSource
) : SakeShopRepository {

    override suspend fun getAllSakeShops(): Result<List<SakeShopPreview>> {
        return try {
            val shops = localDataSource.getAllShops().map { it.toSakeShopPreview() }
            Result.Success(shops)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getSakeShopDetail(id: String): Result<SakeShopDetail> {
        return try {
            val shop = localDataSource.getShopById(id)?.toSakeShopDetail()
            if (shop != null) {
                Result.Success(shop)
            } else {
                Result.Failure(Exception("Shop with id $id not found"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
