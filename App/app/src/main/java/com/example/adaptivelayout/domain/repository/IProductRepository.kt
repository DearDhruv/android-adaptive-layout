package com.example.adaptivelayout.domain.repository

/*
 *  IProductRepository.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import com.example.adaptivelayout.domain.model.ProductDomain
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for product operations
 * Defines the contract for data operations
 */
interface IProductRepository {
    fun getProducts(): Flow<Result<List<ProductDomain>>>
    fun getProductById(productId: String): Flow<Result<ProductDomain>>
}
