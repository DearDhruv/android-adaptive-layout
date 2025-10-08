package com.deardhruv.adaptivelayout.presentation.navigation

/*
 *  AdaptiveNavigationScaffold.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.deardhruv.adaptivelayout.presentation.components.AdaptiveProductListDetailLayout
import com.deardhruv.adaptivelayout.presentation.home.HomeScreen
import com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
import com.deardhruv.adaptivelayout.presentation.settings.SettingsScreen
import com.deardhruv.adaptivelayout.util.WindowInfo

/**
 * Adaptive navigation scaffold that changes navigation UI based on screen size
 * - Compact: Bottom Navigation
 * - Medium: Navigation Rail
 * - Expanded: Navigation Rail (or Drawer)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveNavigationScaffold(
    windowInfo: WindowInfo,
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    var currentDestination by remember { mutableStateOf(AppDestination.HOME) }

    // Determine navigation type based on window size
    val showNavigationRail = windowInfo.widthSizeClass == WindowWidthSizeClass.MEDIUM ||
            windowInfo.widthSizeClass == WindowWidthSizeClass.EXPANDED
    val showBottomBar = windowInfo.widthSizeClass == WindowWidthSizeClass.COMPACT

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Adaptive Layout Demo")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    AppDestination.entries.forEach { destination ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = { Text(destination.label) },
                            selected = currentDestination == destination,
                            onClick = { currentDestination = destination }
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Navigation Rail for medium and expanded screens
            if (showNavigationRail) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    AppDestination.entries.forEach { destination ->
                        NavigationRailItem(
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = { Text(destination.label) },
                            selected = currentDestination == destination,
                            onClick = { currentDestination = destination }
                        )
                    }
                }
            }

            // Main content area
            Box(modifier = Modifier.fillMaxSize()) {
                when (currentDestination) {
                    AppDestination.HOME -> {
                        HomeScreen(windowInfo = windowInfo)
                    }
                    AppDestination.PRODUCTS -> {
                        AdaptiveProductListDetailLayout(
                            viewModel = productViewModel
                        )
                    }
                    AppDestination.SETTINGS -> {
                        SettingsScreen(windowInfo = windowInfo)
                    }
                }
            }
        }
    }
}
