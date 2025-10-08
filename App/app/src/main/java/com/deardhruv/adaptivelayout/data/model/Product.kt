package com.deardhruv.adaptivelayout.data.model

/*
 *  Product.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

/**
 * Data layer model for Product
 * This represents the raw data structure
 */
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val rating: Float,
    val inStock: Boolean
)
