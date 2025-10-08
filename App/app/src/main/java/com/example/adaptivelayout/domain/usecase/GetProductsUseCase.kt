package com.example.adaptivelayout.domain.usecase

/*
 *  GetProductsUseCase.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

import com.example.adaptivelayout.domain.model.ProductDomain
import com.example.adaptivelayout.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting all products
 * Encapsulates business logic for fetching products
 */
class GetProductsUseCase(
    private val repository: IProductRepository
) {
    operator fun invoke(): Flow<Result<List<ProductDomain>>> {
        return repository.getProducts()
    }
}
