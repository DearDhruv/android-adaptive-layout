package com.deardhruv.adaptivelayout.data.repository

/*
 *  ProductRepositoryImpl.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import com.deardhruv.adaptivelayout.data.model.Product
import com.deardhruv.adaptivelayout.data.source.ProductDataSource
import com.deardhruv.adaptivelayout.domain.model.ProductDomain
import com.deardhruv.adaptivelayout.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implementation of product repository
 * Handles data operations and mapping between data and domain models
 */
class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : IProductRepository {

    override fun getProducts(): Flow<Result<List<ProductDomain>>> = flow {
        try {
            val products = dataSource.getProducts()
            val domainProducts = products.map { it.toDomain() }
            emit(Result.success(domainProducts))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getProductById(productId: String): Flow<Result<ProductDomain>> = flow {
        try {
            val product = dataSource.getProductById(productId)
            if (product != null) {
                emit(Result.success(product.toDomain()))
            } else {
                emit(Result.failure(Exception("Product not found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Maps data model to domain model
     */
    private fun Product.toDomain(): ProductDomain {
        return ProductDomain(
            id = id,
            name = name,
            description = description,
            formattedPrice = "$${"%.2f".format(price)}",
            imageUrl = imageUrl,
            category = category,
            rating = rating,
            isAvailable = inStock
        )
    }
}
