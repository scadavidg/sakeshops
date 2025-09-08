package org.example.project.domain.usecase

import org.example.project.domain.model.Result
import org.example.project.domain.model.SakeShopPreview
import org.example.project.domain.repository.SakeShopRepository

class GetAllSakeShopsUseCase(
    private val repository: SakeShopRepository
) {
    suspend operator fun invoke(): Result<List<SakeShopPreview>> {
        return repository.getAllSakeShops()
    }
}
