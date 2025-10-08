package com.deardhruv.adaptivelayout.util

/*
 *  WindowInfo.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

/**
 * Remembers window information including size class and posture
 */
@Composable
fun rememberWindowInfo(): WindowInfo {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val configuration = LocalConfiguration.current

    return WindowInfo(
        windowSizeClass = windowAdaptiveInfo.windowSizeClass,
        windowPosture = windowAdaptiveInfo.windowPosture,
        screenWidthDp = configuration.screenWidthDp.dp,
        screenHeightDp = configuration.screenHeightDp.dp
    )
}

/**
 * Contains window size and posture information
 */
data class WindowInfo(
    val windowSizeClass: WindowSizeClass,
    val windowPosture: androidx.compose.material3.adaptive.Posture,
    val screenWidthDp: Dp,
    val screenHeightDp: Dp
) {
    val widthSizeClass: WindowWidthSizeClass
        get() = windowSizeClass.windowWidthSizeClass

    val heightSizeClass: WindowHeightSizeClass
        get() = windowSizeClass.windowHeightSizeClass

    fun isCompact() = widthSizeClass == WindowWidthSizeClass.COMPACT
    fun isMedium() = widthSizeClass == WindowWidthSizeClass.MEDIUM
    fun isExpanded() = widthSizeClass == WindowWidthSizeClass.EXPANDED
}
