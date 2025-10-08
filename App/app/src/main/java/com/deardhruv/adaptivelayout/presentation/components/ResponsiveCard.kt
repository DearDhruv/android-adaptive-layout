package com.deardhruv.adaptivelayout.presentation.components

/*
 *  ResponsiveCard.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.deardhruv.adaptivelayout.domain.model.ProductDomain

/**
 * Responsive product card that adapts to available width
 * Shows horizontal layout on wide screens, vertical on narrow screens
 */
@Composable
fun ResponsiveProductCard(
    product: ProductDomain,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val useHorizontalLayout = maxWidth > 600.dp

        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            if (useHorizontalLayout) {
                HorizontalProductCardContent(product)
            } else {
                VerticalProductCardContent(product)
            }
        }
    }
}

@Composable
private fun HorizontalProductCardContent(product: ProductDomain) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier
                .size(120.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.formattedPrice,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun VerticalProductCardContent(product: ProductDomain) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.formattedPrice,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
