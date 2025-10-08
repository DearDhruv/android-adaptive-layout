package com.deardhruv.adaptivelayout.presentation.navigation

/*
 *  NavigationDestinations.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Navigation destinations for the app
 */
enum class AppDestination(
    val icon: ImageVector,
    val label: String,
    val contentDescription: String
) {
    HOME(
        icon = Icons.Default.Home,
        label = "Home",
        contentDescription = "Navigate to Home"
    ),
    PRODUCTS(
        icon = Icons.Default.ShoppingCart,
        label = "Products",
        contentDescription = "Navigate to Products"
    ),
    SETTINGS(
        icon = Icons.Default.Settings,
        label = "Settings",
        contentDescription = "Navigate to Settings"
    )
}
