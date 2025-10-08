package com.example.adaptivelayout.domain.model

/*
 *  ProductDomain.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

/**
 * Domain model for Product
 * This represents the business logic layer model
 */
data class ProductDomain(
    val id: String,
    val name: String,
    val description: String,
    val formattedPrice: String,
    val imageUrl: String,
    val category: String,
    val rating: Float,
    val isAvailable: Boolean
)
