package com.deardhruv.adaptivelayout.presentation.components

/*
 *  TopBar.kt
 *
 *  Created by Dhruv Patel on 09/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.deardhruv.adaptivelayout.util.UpdateSystemBars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    titleContent: @Composable () -> Unit,
    isCenterContent: Boolean,
    showBack: Boolean = true,
    onBackClick: (() -> Unit)? = null,
) {
    Box(modifier = Modifier.fillMaxWidth()) {

        val isScrolled =
            remember { derivedStateOf { (scrollBehavior?.state?.contentOffset ?: 0f) < -100f } }

        val topAppBarElementColor = if (isScrolled.value) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onSurface

        val containerColor = if (isScrolled.value) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface

        val colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = containerColor,
            navigationIconContentColor = topAppBarElementColor,
            titleContentColor = topAppBarElementColor,
            actionIconContentColor = topAppBarElementColor,
        )

        // Update status bar color based on scroll
//        val statusBarColor = if (isScrolled.value) {
//            MaterialTheme.colorScheme.surfaceContainer
//        } else {
//            MaterialTheme.colorScheme.primaryContainer
//        }

        UpdateSystemBars(
            statusBarColor = MaterialTheme.colorScheme.primaryContainer,
            navigationBarColor = MaterialTheme.colorScheme.primaryContainer,
            isDarkIcons = !isSystemInDarkTheme()
        )

        if (isCenterContent) {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior,
                title = titleContent,
                navigationIcon = {
                    if (showBack) {
                        IconButton(onClick = { onBackClick?.invoke() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    }
                },
                colors = colors,
            )
        } else {
            TopAppBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior,
                title = titleContent,
                navigationIcon = {
                    if (showBack) {
                        IconButton(onClick = { onBackClick?.invoke() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    }
                },
                colors = colors,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefaults.alEnterAlwaysScrollBehavior() = this.enterAlwaysScrollBehavior()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefaults.alPinnedScrollBehavior() = this.pinnedScrollBehavior()


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTitle(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    isCenterContent: Boolean,
    showBack: Boolean = true,
    onBackClick: (() -> Unit)? = null,
) {
    TopBar(
        scrollBehavior = scrollBehavior,
        titleContent = {
            Text(
                modifier = Modifier,
                text = title,
                maxLines = 1,
                style = MaterialTheme.typography.headlineLarge,
            )
        },
        isCenterContent = isCenterContent,
        showBack = showBack,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
fun TopBarTitlePreview() {
    val scrollBehavior = TopAppBarDefaults.alPinnedScrollBehavior()
    TopBarTitle(
        scrollBehavior = scrollBehavior,
        title = "Title",
        isCenterContent = false,
        showBack = true
    )
}

