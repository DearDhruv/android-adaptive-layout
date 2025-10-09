package com.deardhruv.adaptivelayout.util

/*
 *  SystemBarController.kt
 *
 *  Created by Dhruv Patel on 09/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Updates system status bar color
 */
@Composable
fun UpdateSystemBars(
    statusBarColor: Color,
    navigationBarColor: Color = MaterialTheme.colorScheme.surface,
    isDarkIcons: Boolean = false
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = isDarkIcons
                isAppearanceLightNavigationBars = isDarkIcons
            }
        }
    }
}
