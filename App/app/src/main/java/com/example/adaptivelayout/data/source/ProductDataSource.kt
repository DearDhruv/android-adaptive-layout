package com.example.adaptivelayout.data.source

/*
 *  ProductDataSource.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

import com.example.adaptivelayout.data.model.Product
import kotlinx.coroutines.delay

/**
 * Data source for products
 * In a real app, this would be an API service or database
 */
class ProductDataSource {

    suspend fun getProducts(): List<Product> {
        // Simulate network delay
        delay(500)

        return List(30) { index ->
            Product(
                id = "product_$index",
                name = "Product ${index + 1}",
                description = "This is a detailed description for product ${index + 1}. " +
                        "It showcases adaptive layout capabilities across different screen sizes " +
                        "including phones, tablets, foldables, and desktop windows. " +
                        "The product features high quality materials and excellent craftsmanship.",
                price = (index + 1) * 10.99,
                imageUrl = "https://picsum.photos/seed/$index/400/300",
                category = listOf("Electronics", "Clothing", "Home", "Books", "Sports")[index % 5],
                rating = 3.5f + (index % 3) * 0.5f,
                inStock = index % 7 != 0
            )
        }
    }

    suspend fun getProductById(productId: String): Product? {
        delay(300)
        return getProducts().find { it.id == productId }
    }
}
