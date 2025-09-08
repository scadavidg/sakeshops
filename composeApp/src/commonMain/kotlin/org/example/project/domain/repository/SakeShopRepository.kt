package org.example.project.domain.repository

import org.example.project.domain.model.Result
import org.example.project.domain.model.SakeShopDetail
import org.example.project.domain.model.SakeShopPreview

interface SakeShopRepository {
    suspend fun getAllSakeShops(): Result<List<SakeShopPreview>>
    suspend fun getSakeShopDetail(id: String): Result<SakeShopDetail>
}
