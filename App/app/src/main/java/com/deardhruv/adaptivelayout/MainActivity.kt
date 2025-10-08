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
import com.deardhruv.adaptivelayout.presentation.navigation.AdaptiveNavigationScaffold
import com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
import com.deardhruv.adaptivelayout.ui.theme.AdaptiveLayoutTheme
import com.deardhruv.adaptivelayout.util.DevicePostureType
import com.deardhruv.adaptivelayout.util.detectDevicePosture
import com.deardhruv.adaptivelayout.util.rememberWindowInfo

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

@Composable
fun AdaptiveLayoutApp() {
    val windowInfo = rememberWindowInfo()
    val postureType = detectDevicePosture(windowInfo.windowPosture)

    // Use remember to keep ViewModel instance across recompositions
    // ViewModel itself handles configuration changes
    val productViewModel: ProductViewModel = viewModel(
        factory = ProductViewModelFactory()
    )

    when (postureType) {
        DevicePostureType.TABLETOP,
        DevicePostureType.BOOK,
        DevicePostureType.NORMAL -> {
            AdaptiveNavigationScaffold(
                windowInfo = windowInfo,
                productViewModel = productViewModel
            )
        }
    }
}

class ProductViewModelFactory : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
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



