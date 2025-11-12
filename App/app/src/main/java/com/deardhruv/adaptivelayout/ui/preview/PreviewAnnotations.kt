package com.deardhruv.adaptivelayout.ui.preview

/*
 *  PreviewAnnotations.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multi-preview annotation for compact screens (phones)
 */
@Preview(
    name = "Phone - Portrait",
    device = "spec:width=411dp,height=891dp,dpi=420",
    showBackground = true,
    backgroundColor = 0x81248114
)
@Preview(
    name = "Phone - Landscape",
    device = "spec:width=891dp,height=411dp,dpi=420",
    showBackground = true
)
annotation class PhonePreviews

/**
 * Multi-preview annotation for tablets
 */
@Preview(
    name = "Tablet - Portrait",
    device = "spec:width=800dp,height=1280dp,dpi=240",
    showBackground = true
)
@Preview(
    name = "Tablet - Landscape",
    device = "spec:width=1280dp,height=800dp,dpi=240",
    showBackground = true
)
annotation class TabletPreviews

/**
 * Multi-preview annotation for foldables
 */
@Preview(
    name = "Foldable - Closed",
    device = "spec:width=673dp,height=841dp,dpi=480",
    showBackground = true
)
@Preview(
    name = "Foldable - Open",
    device = "spec:width=1480dp,height=841dp,dpi=480",
    showBackground = true
)
annotation class FoldablePreviews

/**
 * Multi-preview annotation for desktop
 */
@Preview(
    name = "Desktop - Small",
    device = "spec:width=1024dp,height=768dp,dpi=160",
    showBackground = true
)
@Preview(
    name = "Desktop - Large",
    device = "spec:width=1920dp,height=1080dp,dpi=160",
    showBackground = true
)
annotation class DesktopPreviews

/**
 * Multi-preview annotation for light and dark themes
 */
@Preview(
    name = "Light Theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ThemePreviews

/**
 * Complete multi-device preview
 */
@PhonePreviews
@TabletPreviews
@FoldablePreviews
annotation class DevicePreviews

/**
 * All adaptive layout previews
 */
@PhonePreviews
@TabletPreviews
@FoldablePreviews
@DesktopPreviews
annotation class AllDevicePreviews
