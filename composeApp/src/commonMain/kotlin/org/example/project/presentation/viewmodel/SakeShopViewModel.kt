package org.example.project.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.domain.model.Result
import org.example.project.domain.model.SakeShopDetail
import org.example.project.domain.model.SakeShopPreview
import org.example.project.domain.usecase.GetAllSakeShopsUseCase
import org.example.project.domain.usecase.GetSakeShopDetailUseCase

class SakeShopViewModel(
    private val getAllSakeShopsUseCase: GetAllSakeShopsUseCase,
    private val getSakeShopDetailUseCase: GetSakeShopDetailUseCase
) {
    
    // State for shop list
    var shopList by mutableStateOf<List<SakeShopPreview>>(emptyList())
        private set
    
    var isLoadingList by mutableStateOf(false)
        private set
    
    var listError by mutableStateOf<String?>(null)
        private set
    
    // State for shop detail
    var selectedShop by mutableStateOf<SakeShopDetail?>(null)
        private set
    
    var isLoadingDetail by mutableStateOf(false)
        private set
    
    var detailError by mutableStateOf<String?>(null)
        private set
    
    fun loadShopList() {
        CoroutineScope(Dispatchers.Main).launch {
            isLoadingList = true
            listError = null
            
            val result = getAllSakeShopsUseCase()
            when (result) {
                is Result.Success -> {
                    shopList = result.data
                    isLoadingList = false
                }
                is Result.Failure -> {
                    listError = result.error.message ?: "Error loading shops"
                    isLoadingList = false
                }
                is Result.Loading -> {
                    // Already loading
                }
            }
        }
    }
    
    fun loadShopDetail(shopId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            isLoadingDetail = true
            detailError = null
            
            val result = getSakeShopDetailUseCase(shopId)
            when (result) {
                is Result.Success -> {
                    selectedShop = result.data
                    isLoadingDetail = false
                }
                is Result.Failure -> {
                    detailError = result.error.message ?: "Error loading shop detail"
                    isLoadingDetail = false
                }
                is Result.Loading -> {
                    // Already loading
                }
            }
        }
    }
    
    fun clearSelectedShop() {
        selectedShop = null
        detailError = null
    }
    
    fun clearListError() {
        listError = null
    }
    
    fun clearDetailError() {
        detailError = null
    }
}
