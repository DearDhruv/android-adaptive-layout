package com.deardhruv.adaptivelayout.presentation.navigation

/*
 *  AdaptiveNavigationScaffold.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deardhruv.adaptivelayout.presentation.components.AdaptiveProductListDetailLayout
import com.deardhruv.adaptivelayout.presentation.custom.CustomAdaptiveLayoutScreen
import com.deardhruv.adaptivelayout.presentation.home.HomeScreen
import com.deardhruv.adaptivelayout.presentation.products.ProductViewModel
import com.deardhruv.adaptivelayout.presentation.settings.SettingsScreen
import com.deardhruv.adaptivelayout.util.DevicePostureType
import com.deardhruv.adaptivelayout.util.WindowInfo


/**
 * Adaptive navigation scaffold that changes navigation UI based on screen size
 * - Compact: Bottom Navigation
 * - Medium: Navigation Rail
 * - Expanded: Navigation Rail (or Drawer)
 */


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveNavigationScaffold(
    postureType: DevicePostureType,
    windowInfo: WindowInfo,
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier,
) {

    when (postureType) {
        DevicePostureType.TABLETOP,
        DevicePostureType.BOOK,
        DevicePostureType.NORMAL -> {

        }
    }
    var navigationState by rememberSaveable(stateSaver = NavigationState.Saver) {
        mutableStateOf(NavigationState())
    }

    val showNavigationRail =
        windowInfo.widthSizeClass == WindowWidthSizeClass.Medium || windowInfo.widthSizeClass == WindowWidthSizeClass.Expanded
    val showBottomBar = windowInfo.widthSizeClass == WindowWidthSizeClass.Compact

    Scaffold(
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
                            selected = navigationState.currentDestination == destination,
                            onClick = {
                                navigationState = navigationState.copy(
                                    currentDestination = destination
                                )
                            }
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Row(modifier = Modifier.fillMaxSize()) {
//            if (showNavigationRail) {
//                NavigationSuiteScaffold(
//                    navigationSuiteItems = {
//                        AppDestination.entries.forEach {
//                            item(
//                                selected = navigationState.currentDestination == it,
//                                onClick = {
//                                    navigationState = navigationState.copy(
//                                        currentDestination = it
//                                    )
//                                },
//                                label = { Text(it.label) },
//                                icon = {
//                                    Icon(
//                                        imageVector = it.icon,
//                                        contentDescription = it.contentDescription
//                                    )
//                                },
//                                alwaysShowLabel = true,
//                            )
//                        }
//                    },
//                    layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true))
//                )
//            }

            if (showNavigationRail) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    AppDestination.entries.forEach { destination ->
                        NavigationRailItem(
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = { Text(destination.label) },
                            selected = navigationState.currentDestination == destination,
                            onClick = {
                                navigationState = navigationState.copy(
                                    currentDestination = destination
                                )
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
            ) {
                when (navigationState.currentDestination) {
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

                    AppDestination.CUSTOM -> {
                        // Show the custom adaptive layout screen here
                        CustomAdaptiveLayoutScreen(windowInfo = windowInfo)
                    }
                }
            }
        }
    }
}
