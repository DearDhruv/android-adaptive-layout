package com.deardhruv.adaptivelayout.presentation.custom

/*
 *  CustomAdaptiveLayoutScreen.kt
 *
 *  Created by Dhruv Patel on 07/11/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deardhruv.adaptivelayout.presentation.widgets.AdaptiveButtonsEnd
import com.deardhruv.adaptivelayout.presentation.widgets.MoreButtonTextEnd
import com.deardhruv.adaptivelayout.presentation.widgets.TextWithBadgeEnd
import com.deardhruv.adaptivelayout.util.WindowInfo
import com.deardhruv.adaptivelayout.util.isPortraitMode

@Composable
fun CustomAdaptiveLayoutScreen(
    windowInfo: WindowInfo,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    when (windowInfo.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            if (isPortraitMode(context)) {
                CompactPortraitLayout(modifier)
            } else {
                CompactLandscapeLayout(modifier)
            }
        }

        WindowWidthSizeClass.Medium -> {
            if (isPortraitMode(context)) {
                MediumPortraitLayout(modifier)
            } else {
                MediumLandscapeLayout(modifier)
            }
        }

        WindowWidthSizeClass.Expanded -> {
            ExpandedLayout(modifier)
        }
    }
}

@Composable
private fun CompactPortraitLayout(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Compact Portrait (Phone)")
        MoreButtonTextEnd()
        TextWithBadgeEnd()
        AdaptiveButtonsEnd()
    }
}

@Composable
private fun CompactLandscapeLayout(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        MoreButtonTextEnd()
        TextWithBadgeEnd()
        AdaptiveButtonsEnd()
    }
}

@Composable
private fun MediumPortraitLayout(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Medium Portrait (Tablet)")
        MoreButtonTextEnd()
        TextWithBadgeEnd()
        AdaptiveButtonsEnd()
    }
}

@Composable
private fun MediumLandscapeLayout(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Medium Landscape (Tablet)")
            MoreButtonTextEnd()
            TextWithBadgeEnd()
            AdaptiveButtonsEnd()
        }
        // Placeholder for additional content
        Box(modifier = Modifier.weight(1f)) {
            Text("Extra content area")
        }
    }
}

@Composable
private fun ExpandedLayout(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text("Expanded Mode (Monitor)")
            MoreButtonTextEnd()
            TextWithBadgeEnd()
            AdaptiveButtonsEnd()
        }
        Box(
            modifier = Modifier.weight(3f)
        ) {
            Text("Expanded content area for monitor or desktop screens")
        }
    }
}
