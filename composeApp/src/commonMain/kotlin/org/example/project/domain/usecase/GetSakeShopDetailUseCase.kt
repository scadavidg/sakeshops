package org.example.project.domain.usecase

import org.example.project.domain.model.Result
import org.example.project.domain.model.SakeShopDetail
import org.example.project.domain.repository.SakeShopRepository

class GetSakeShopDetailUseCase(
    private val repository: SakeShopRepository
) {
    suspend operator fun invoke(id: String): Result<SakeShopDetail> {
        return repository.getSakeShopDetail(id)
    }
}
