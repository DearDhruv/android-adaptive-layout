package com.deardhruv.adaptivelayout.presentation.products

/*
 *  ProductUiState.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import com.deardhruv.adaptivelayout.domain.model.ProductDomain

/**
 * UI state for product screens
 */
sealed interface ProductUiState {
    data object Loading : ProductUiState
    data class Success(val products: List<ProductDomain>) : ProductUiState
    data class Error(val message: String) : ProductUiState
}

/**
 * UI state for product detail
 */
sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Success(val product: ProductDomain) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}
