package com.example.adaptivelayout.presentation.products

/*
 *  ProductViewModel.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adaptivelayout.domain.model.ProductDomain
import com.example.adaptivelayout.domain.usecase.GetProductByIdUseCase
import com.example.adaptivelayout.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for product list and detail screens
 * Follows MVVM architecture pattern
 */
class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _selectedProduct = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val selectedProduct: StateFlow<ProductDetailUiState> = _selectedProduct.asStateFlow()

    init {
        loadProducts()
    }

    /**
     * Load all products
     */
    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            getProductsUseCase().collect { result ->
                _uiState.value = result.fold(
                    onSuccess = { ProductUiState.Success(it) },
                    onFailure = { ProductUiState.Error(it.message ?: "Unknown error") }
                )
            }
        }
    }

    /**
     * Load product by ID
     */
    fun loadProductById(productId: String) {
        viewModelScope.launch {
            _selectedProduct.value = ProductDetailUiState.Loading
            getProductByIdUseCase(productId).collect { result ->
                _selectedProduct.value = result.fold(
                    onSuccess = { ProductDetailUiState.Success(it) },
                    onFailure = { ProductDetailUiState.Error(it.message ?: "Product not found") }
                )
            }
        }
    }

    /**
     * Get product from current state by ID
     */
    fun getProductById(productId: String): ProductDomain? {
        return when (val state = _uiState.value) {
            is ProductUiState.Success -> state.products.find { it.id == productId }
            else -> null
        }
    }
}
