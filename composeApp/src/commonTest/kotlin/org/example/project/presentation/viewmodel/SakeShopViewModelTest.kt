package org.example.project.presentation.viewmodel

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SakeShopViewModelTest {
    
    @Test
    fun `viewModel should be instantiable`() {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val getAllSakeShopsUseCase = org.example.project.domain.usecase.GetAllSakeShopsUseCase(repository)
        val getSakeShopDetailUseCase = org.example.project.domain.usecase.GetSakeShopDetailUseCase(repository)
        
        // When
        val viewModel = SakeShopViewModel(getAllSakeShopsUseCase, getSakeShopDetailUseCase)
        
        // Then
        assertTrue(viewModel.shopList.isEmpty(), "Initial shop list should be empty")
        assertFalse(viewModel.isLoadingList, "Initial loading state should be false")
        assertEquals(null, viewModel.listError, "Initial list error should be null")
        assertEquals(null, viewModel.selectedShop, "Initial selected shop should be null")
        assertFalse(viewModel.isLoadingDetail, "Initial detail loading state should be false")
        assertEquals(null, viewModel.detailError, "Initial detail error should be null")
    }
    
    @Test
    fun `clearSelectedShop should clear selected shop`() {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val getAllSakeShopsUseCase = org.example.project.domain.usecase.GetAllSakeShopsUseCase(repository)
        val getSakeShopDetailUseCase = org.example.project.domain.usecase.GetSakeShopDetailUseCase(repository)
        val viewModel = SakeShopViewModel(getAllSakeShopsUseCase, getSakeShopDetailUseCase)
        
        // When
        viewModel.clearSelectedShop()
        
        // Then
        assertEquals(null, viewModel.selectedShop)
        assertEquals(null, viewModel.detailError)
    }
    
    @Test
    fun `clearListError should clear list error`() {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val getAllSakeShopsUseCase = org.example.project.domain.usecase.GetAllSakeShopsUseCase(repository)
        val getSakeShopDetailUseCase = org.example.project.domain.usecase.GetSakeShopDetailUseCase(repository)
        val viewModel = SakeShopViewModel(getAllSakeShopsUseCase, getSakeShopDetailUseCase)
        
        // When
        viewModel.clearListError()
        
        // Then
        assertEquals(null, viewModel.listError)
    }
    
    @Test
    fun `clearDetailError should clear detail error`() {
        // Given
        val repository = org.example.project.data.repository.SakeShopRepositoryImpl(
            org.example.project.data.source.local.ShopLocalDataSourceImpl()
        )
        val getAllSakeShopsUseCase = org.example.project.domain.usecase.GetAllSakeShopsUseCase(repository)
        val getSakeShopDetailUseCase = org.example.project.domain.usecase.GetSakeShopDetailUseCase(repository)
        val viewModel = SakeShopViewModel(getAllSakeShopsUseCase, getSakeShopDetailUseCase)
        
        // When
        viewModel.clearDetailError()
        
        // Then
        assertEquals(null, viewModel.detailError)
    }
}
