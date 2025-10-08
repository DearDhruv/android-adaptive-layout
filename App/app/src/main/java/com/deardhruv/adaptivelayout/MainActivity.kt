package com.deardhruv.adaptivelayout


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deardhruv.adaptivelayout.data.repository.ProductRepositoryImpl
import com.deardhruv.adaptivelayout.data.source.ProductDataSource
import com.deardhruv.adaptivelayout.domain.usecase.GetProductByIdUseCase
import com.deardhruv.adaptivelayout.domain.usecase.GetProductsUseCase
import com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
import com.deardhruv.adaptivelayout.ui.theme.AdaptiveLayoutTheme

/**
 * Main Activity - Entry point for the adaptive layout demo app
 * Demonstrates Android 17 adaptive layout requirements
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AdaptiveLayoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AdaptiveLayoutApp()
                }
            }
        }
    }
}

/**
 * Main app composable with dependency injection
 */
@Composable
fun AdaptiveLayoutApp() {
    // Get window information
    val windowInfo = _root_ide_package_.com.deardhruv.adaptivelayout.util.rememberWindowInfo()
    val postureType = _root_ide_package_.com.deardhruv.adaptivelayout.util.detectDevicePosture(windowInfo.windowPosture)

    // Manual dependency injection (can be replaced with Hilt/Koin)
    val productViewModel: com.deardhruv.adaptivelayout.presentation.products.ProductViewModel = viewModel(
        factory = ProductViewModelFactory()
    )

    // Handle different device postures
    when (postureType) {
        _root_ide_package_.com.deardhruv.adaptivelayout.util.DevicePostureType.TABLETOP -> {
            // Tabletop mode for foldables (video watching mode)
            TabletopModeLayout(windowInfo, productViewModel)
        }

        _root_ide_package_.com.deardhruv.adaptivelayout.util.DevicePostureType.BOOK -> {
            // Book mode for foldables (reading mode)
            BookModeLayout(windowInfo, productViewModel)
        }

        _root_ide_package_.com.deardhruv.adaptivelayout.util.DevicePostureType.NORMAL -> {
            // Standard layout
            _root_ide_package_.com.deardhruv.adaptivelayout.presentation.navigation.AdaptiveNavigationScaffold(
                windowInfo = windowInfo,
                productViewModel = productViewModel
            )
        }
    }
}

/**
 * Tabletop mode layout (horizontal fold - video watching)
 */
@Composable
private fun TabletopModeLayout(
    windowInfo: com.deardhruv.adaptivelayout.util.WindowInfo,
    productViewModel: com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
) {
    // For tabletop mode, split UI vertically
    // Top half: main content, Bottom half: controls
    _root_ide_package_.com.deardhruv.adaptivelayout.presentation.navigation.AdaptiveNavigationScaffold(
        windowInfo = windowInfo,
        productViewModel = productViewModel
    )
}

/**
 * Book mode layout (vertical fold - reading mode)
 */
@Composable
private fun BookModeLayout(
    windowInfo: com.deardhruv.adaptivelayout.util.WindowInfo,
    productViewModel: com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
) {
    // For book mode, split UI horizontally
    // This is already handled by NavigableListDetailPaneScaffold
    _root_ide_package_.com.deardhruv.adaptivelayout.presentation.navigation.AdaptiveNavigationScaffold(
        windowInfo = windowInfo,
        productViewModel = productViewModel
    )
}

/**
 * ViewModel Factory for manual dependency injection
 * Replace with Hilt in production
 */
class ProductViewModelFactory : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            // Create dependencies
            val dataSource = ProductDataSource()
            val repository = ProductRepositoryImpl(dataSource)
            val getProductsUseCase = GetProductsUseCase(repository)
            val getProductByIdUseCase = GetProductByIdUseCase(repository)

            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(getProductsUseCase, getProductByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




