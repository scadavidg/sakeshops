package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.data.repository.SakeShopRepositoryImpl
import org.example.project.data.source.local.ShopLocalDataSourceImpl
import org.example.project.domain.usecase.GetAllSakeShopsUseCase
import org.example.project.domain.usecase.GetSakeShopDetailUseCase
import org.example.project.presentation.navigation.Navigation
import org.example.project.presentation.viewmodel.SakeShopViewModel

@Composable
fun App() {
    MaterialTheme {
        val repository = remember {
            SakeShopRepositoryImpl(ShopLocalDataSourceImpl())
        }
        val getAllSakeShopsUseCase = remember { GetAllSakeShopsUseCase(repository) }
        val getSakeShopDetailUseCase = remember { GetSakeShopDetailUseCase(repository) }
        val viewModel = remember {
            SakeShopViewModel(getAllSakeShopsUseCase, getSakeShopDetailUseCase)
        }

        Navigation(viewModel = viewModel)
    }
}
