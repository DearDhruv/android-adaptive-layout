package com.deardhruv.adaptivelayout.presentation.navigation

/*
 *  AdaptiveNavigationScaffold.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.deardhruv.adaptivelayout.presentation.components.AdaptiveProductListDetailLayout
import com.deardhruv.adaptivelayout.presentation.custom.CustomAdaptiveLayoutScreen
import com.deardhruv.adaptivelayout.presentation.home.HomeScreen
import com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
import com.deardhruv.adaptivelayout.presentation.settings.SettingsScreen
import com.deardhruv.adaptivelayout.util.DevicePostureType
import com.deardhruv.adaptivelayout.util.WindowInfo


/**
 * Adaptive navigation scaffold that automatically selects the correct navigation UI:
 * - Compact: Bottom Navigation Bar
 * - Medium:  Navigation Rail
 * - Expanded: Navigation Drawer
 *
 * Uses [NavigationSuiteScaffold] which handles all form factors natively.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveNavigationScaffold(
    @Suppress("UNUSED_PARAMETER") postureType: DevicePostureType, // reserved for foldable-specific layouts
    windowInfo: WindowInfo,
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier,
) {
    var navigationState by rememberSaveable(stateSaver = NavigationState.Saver) {
        mutableStateOf(NavigationState())
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestination.entries.forEach { destination ->
                item(
                    selected = navigationState.currentDestination == destination,
                    onClick = {
                        navigationState = navigationState.copy(
                            currentDestination = destination
                        )
                    },
                    label = { Text(destination.label) },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.contentDescription
                        )
                    },
                    alwaysShowLabel = true,
                )
            }
        },
        layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
            currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)
        ),
        modifier = modifier,
    ) {
        when (navigationState.currentDestination) {
            AppDestination.HOME -> HomeScreen(windowInfo = windowInfo)
            AppDestination.PRODUCTS -> AdaptiveProductListDetailLayout(viewModel = productViewModel)
            AppDestination.SETTINGS -> SettingsScreen(windowInfo = windowInfo)
            AppDestination.CUSTOM -> CustomAdaptiveLayoutScreen(windowInfo = windowInfo)
        }
    }
}
