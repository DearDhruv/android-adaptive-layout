package com.example.adaptivelayout.domain.usecase

/*
 *  GetProductByIdUseCase.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

import com.example.adaptivelayout.domain.model.ProductDomain
import com.example.adaptivelayout.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting a product by ID
 */
class GetProductByIdUseCase(
    private val repository: IProductRepository
) {
    operator fun invoke(productId: String): Flow<Result<ProductDomain>> {
        return repository.getProductById(productId)
    }
}
