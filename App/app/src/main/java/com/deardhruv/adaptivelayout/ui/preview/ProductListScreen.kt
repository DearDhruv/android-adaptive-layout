package com.deardhruv.adaptivelayout.ui.preview

import androidx.compose.runtime.Composable
import com.deardhruv.adaptivelayout.domain.model.ProductDomain
import com.deardhruv.adaptivelayout.presentation.products.ProductListItem
import com.deardhruv.adaptivelayout.presentation.products.ProductListPane
import com.deardhruv.adaptivelayout.ui.theme.AdaptiveLayoutTheme

/*
 *  ProductListScreen.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

// Add these preview functions to ProductListScreen.kt

@PhonePreviews
@Composable
private fun ProductListItemPreview() {
    AdaptiveLayoutTheme {
        ProductListItem(
            product = ProductDomain(
                id = "1",
                name = "Sample Product",
                description = "This is a sample product description",
                formattedPrice = "$99.99",
                imageUrl = "https://picsum.photos/400/300",
                category = "Electronics",
                rating = 4.5f,
                isAvailable = true
            ),
            onClick = {}
        )
    }
}

@TabletPreviews
@Composable
private fun ProductListPanePreview() {
    AdaptiveLayoutTheme {
        ProductListPane(
            products = List(10) { index ->
                ProductDomain(
                    id = "product_$index",
                    name = "Product ${index + 1}",
                    description = "Description for product ${index + 1}",
                    formattedPrice = "$${(index + 1) * 10}.99",
                    imageUrl = "https://picsum.photos/400/300?random=$index",
                    category = "Category ${index % 3}",
                    rating = 3.5f + (index % 3) * 0.5f,
                    isAvailable = true
                )
            },
            onProductClick = {}
        )
    }
}
