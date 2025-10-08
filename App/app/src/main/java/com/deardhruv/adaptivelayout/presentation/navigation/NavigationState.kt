package com.deardhruv.adaptivelayout.presentation.navigation

/*
 *  NavigationState.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

/**
 * Navigation state holder that survives configuration changes
 */
data class NavigationState(
    val currentDestination: AppDestination = AppDestination.HOME,
    val selectedProductId: String? = null
) {
    companion object {
        val Saver: Saver<NavigationState, *> = listSaver(
            save = { state ->
                listOf(
                    state.currentDestination.name,
                    state.selectedProductId
                )
            },
            restore = { saved ->
                NavigationState(
                    currentDestination = AppDestination.valueOf(saved[0] as String),
                    selectedProductId = saved[1] as? String
                )
            }
        )
    }
}
