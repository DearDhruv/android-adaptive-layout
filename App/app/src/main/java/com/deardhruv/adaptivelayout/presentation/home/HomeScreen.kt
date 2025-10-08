package com.deardhruv.adaptivelayout.presentation.home

/*
 *  HomeScreen.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Tablet
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.deardhruv.adaptivelayout.util.WindowInfo

/**
 * Home screen showcasing adaptive layout capabilities
 */
@Composable
fun HomeScreen(
    windowInfo: WindowInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header
        Text(
            text = "Android 17 Adaptive Layouts",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = "This app demonstrates adaptive layouts across phones, tablets, foldables, and desktop windows",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Current screen info
        CurrentScreenInfoCard(windowInfo)

        Spacer(modifier = Modifier.height(16.dp))

        // Features
        Text(
            text = "Key Features",
            style = MaterialTheme.typography.headlineMedium
        )

        val features = listOf(
            Triple(Icons.Default.PhoneAndroid, "Window Size Classes", "Compact, Medium, and Expanded layouts"),
            Triple(Icons.Default.Tablet, "List-Detail Layouts", "Adaptive navigation for all screen sizes"),
            Triple(Icons.Default.Computer, "Foldable Support", "Tabletop and Book postures")
        )

        features.forEach { (icon, title, description) ->
            FeatureCard(
                icon = icon,
                title = title,
                description = description
            )
        }
    }
}

@Composable
private fun CurrentScreenInfoCard(windowInfo: WindowInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Current Screen Configuration",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoChip(
                    label = "Width Class",
                    value = when (windowInfo.widthSizeClass) {
                        WindowWidthSizeClass.COMPACT -> "Compact"
                        WindowWidthSizeClass.MEDIUM -> "Medium"
                        WindowWidthSizeClass.EXPANDED -> "Expanded"
                        else -> "Unknown"
                    }
                )

                InfoChip(
                    label = "Screen Width",
                    value = "${windowInfo.screenWidthDp.value.toInt()} dp"
                )
            }
        }
    }
}

@Composable
private fun InfoChip(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = value,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun FeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
