package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.project.presentation.components.ErrorScreen
import org.example.project.presentation.components.LoadingScreen
import org.example.project.presentation.components.SakeShopItem
import org.example.project.presentation.viewmodel.SakeShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SakeShopListScreen(
    navController: NavController,
    viewModel: SakeShopViewModel,
    modifier: Modifier = Modifier
) {
    val shopList = viewModel.shopList
    val isLoading = viewModel.isLoadingList
    val error = viewModel.listError

    LaunchedEffect(Unit) {
        viewModel.loadShopList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sake Shops",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    LoadingScreen()
                }

                error != null -> {
                    ErrorScreen(
                        error = error,
                        onRetry = {
                            viewModel.clearListError()
                            viewModel.loadShopList()
                        }
                    )
                }

                shopList.isEmpty() -> {
                    ErrorScreen(
                        error = "No sake shops available",
                        onRetry = { viewModel.loadShopList() }
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(shopList.size) { index ->
                            val shop = shopList[index]
                            SakeShopItem(
                                shop = shop,
                                onClick = {
                                    navController.navigate("shop_detail/${shop.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
