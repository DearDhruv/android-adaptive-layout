package com.deardhruv.adaptivelayout.util

/*
 *  WindowInfo.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import android.app.Activity
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.Posture
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp

/**
 * Contains up-to-date window size and posture information using latest APIs.
 */
data class WindowInfo @OptIn(ExperimentalMaterial3AdaptiveApi::class) constructor(
    val windowSizeClass: WindowSizeClass,
    val windowPosture: Posture,          // Use Posture if foldables/tablets supported
    val screenWidthDp: Dp,
    val screenHeightDp: Dp
) {
    val widthSizeClass: WindowWidthSizeClass
        get() = windowSizeClass.widthSizeClass

    val heightSizeClass: WindowHeightSizeClass
        get() = windowSizeClass.heightSizeClass

    fun isCompact() = widthSizeClass == WindowWidthSizeClass.Compact
    fun isMedium() = widthSizeClass == WindowWidthSizeClass.Medium
    fun isExpanded() = widthSizeClass == WindowWidthSizeClass.Expanded
}

/**
 * Helper to get up-to-date WindowInfo inside your composables
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun rememberWindowInfo(posture: State<Posture>): WindowInfo {
    val activity = LocalContext.current as Activity
    val configuration = LocalConfiguration.current
    val windowSizeClass = calculateWindowSizeClass(activity)

    return WindowInfo(
        windowSizeClass = windowSizeClass,
        windowPosture = posture.value,
        screenWidthDp = Dp(configuration.screenWidthDp.toFloat()),
        screenHeightDp = Dp(configuration.screenHeightDp.toFloat())
    )
}