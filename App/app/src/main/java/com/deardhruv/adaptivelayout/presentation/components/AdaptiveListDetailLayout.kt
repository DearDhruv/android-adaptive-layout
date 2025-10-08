package com.deardhruv.adaptivelayout.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deardhruv.adaptivelayout.presentation.products.ProductDetailPane
import com.deardhruv.adaptivelayout.presentation.products.ProductDetailUiState
import com.deardhruv.adaptivelayout.presentation.products.ProductListPane
import com.deardhruv.adaptivelayout.presentation.products.ProductUiState
import com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
import kotlinx.coroutines.launch

/**
 * Adaptive list-detail layout using NavigableListDetailPaneScaffold
 * Automatically handles phone (single pane) and tablet (dual pane) layouts
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveProductListDetailLayout(
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedProductState by viewModel.selectedProduct.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<String>()
    val scope = rememberCoroutineScope()

    BackHandler(navigator.canNavigateBack()) {
        scope.launch {
            navigator.navigateBack()
        }
    }

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                when (uiState) {
                    is ProductUiState.Loading -> {
                        LoadingScreen()
                    }

                    is ProductUiState.Success -> {
                        val products = (uiState as ProductUiState.Success).products
                        ProductListPane(
                            products = products,
                            onProductClick = { productId ->
                                viewModel.loadProductById(productId)
                                scope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail,
                                        contentKey = productId
                                    )
                                }
                            }
                        )
                    }

                    is ProductUiState.Error -> {
                        ErrorScreen(
                            message = (uiState as ProductUiState.Error).message,
                            onRetry = { viewModel.loadProducts() }
                        )
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane {
                val selectedProductId = navigator.currentDestination?.contentKey

                // Check if list pane is visible (correct way)
                val isListVisible = navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

                // Load product when detail pane is opened
                LaunchedEffect(selectedProductId) {
                    selectedProductId?.let { productId ->
                        viewModel.loadProductById(productId)
                    }
                }

                when (selectedProductState) {
                    is ProductDetailUiState.Loading -> {
                        LoadingScreen()
                    }

                    is ProductDetailUiState.Success -> {
                        val product = (selectedProductState as ProductDetailUiState.Success).product
                        ProductDetailPane(
                            product = product,
                            onBackClick = { scope.launch { navigator.navigateBack() } },
                            isListVisible = isListVisible
                        )
                    }

                    is ProductDetailUiState.Error -> {
                        ErrorScreen(
                            message = (selectedProductState as ProductDetailUiState.Error).message,
                            onRetry = {
                                selectedProductId?.let { viewModel.loadProductById(it) }
                            }
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}

/**
 * Loading screen composable
 */
@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator()
    }
}

/**
 * Error screen composable
 */
@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            androidx.compose.material3.Text(
                text = message,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                color = androidx.compose.material3.MaterialTheme.colorScheme.error
            )
            androidx.compose.material3.Button(onClick = onRetry) {
                androidx.compose.material3.Text("Retry")
            }
        }
    }
}
