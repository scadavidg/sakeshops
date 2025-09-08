package org.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.screens.SakeShopDetailScreen
import org.example.project.presentation.screens.SakeShopListScreen
import org.example.project.presentation.viewmodel.SakeShopViewModel

@Composable
fun Navigation(viewModel: SakeShopViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "shops") {
        composable("shops") {
            SakeShopListScreen(navController = navController, viewModel = viewModel)
        }
        composable("shop_detail/{shopId}") { backStackEntry ->
            val shopId = backStackEntry.arguments?.getString("shopId") ?: ""
            SakeShopDetailScreen(
                shopId = shopId,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

