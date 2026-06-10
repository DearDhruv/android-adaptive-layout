package com.deardhruv.adaptivelayout.presentation.custom

/*
 *  CustomAdaptiveLayoutScreen.kt
 *
 *  Created by Dhruv Patel on 07/11/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deardhruv.adaptivelayout.presentation.components.TopBarTitle
import com.deardhruv.adaptivelayout.presentation.components.alPinnedScrollBehavior
import com.deardhruv.adaptivelayout.presentation.widgets.AdaptiveButtonsEnd
import com.deardhruv.adaptivelayout.presentation.widgets.MoreButtonTextEnd
import com.deardhruv.adaptivelayout.presentation.widgets.TextWithBadgeEnd
import com.deardhruv.adaptivelayout.util.WindowInfo
import com.deardhruv.adaptivelayout.util.isPortraitMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAdaptiveLayoutScreen(
    windowInfo: WindowInfo,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.alPinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarTitle(
                scrollBehavior = scrollBehavior,
                title = "Custom Layouts",
                isCenterContent = false,
                showBack = false,
            )
        },
    ) { paddingValues ->

        val scrollState = rememberScrollState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            // Informational section describing adaptive layouts
            Text(
                text = "Adaptive Layouts: Are Your Apps Ready?",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = "Android 17 making adaptive layouts mandatory, developers must adopt window size classes to ensure UI adapts smoothly across phones, foldables, tablets, and desktops. It's crucial to handle state correctly on configuration changes and maintain accessibility and performance in adaptive layouts.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp)
            )
            Spacer(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
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
            Text("Expanded content area for more content.")
        }
    }
}
