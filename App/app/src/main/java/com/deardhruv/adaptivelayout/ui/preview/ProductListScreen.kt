package com.deardhruv.adaptivelayout.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.deardhruv.adaptivelayout.presentation.products.ProductDetailPane
import com.deardhruv.adaptivelayout.presentation.products.ProductListPane

/*
 *  ProductListScreen.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

// Add these preview functions to ProductListScreen.kt

//@PhonePreviews
//@Composable
//private fun ProductListItemPreview() {
//    AdaptiveLayoutTheme {
//        ProductListItem(
//            product = ProductDomain(
//                id = "1",
//                name = "Sample Product",
//                description = "This is a sample product description",
//                formattedPrice = "$99.99",
//                imageUrl = "https://picsum.photos/400/300",
//                category = "Electronics",
//                rating = 4.5f,
//                isAvailable = true
//            ),
//            onClick = {}
//        )
//    }
//}
//
//@TabletPreviews
//@Composable
//private fun ProductListPanePreview() {
//    AdaptiveLayoutTheme {
//        ProductListPane(
//            products = List(10) { index ->
//                ProductDomain(
//                    id = "product_$index",
//                    name = "Product ${index + 1}",
//                    description = "Description for product ${index + 1}",
//                    formattedPrice = "$${(index + 1) * 10}.99",
//                    imageUrl = "https://picsum.photos/400/300?random=$index",
//                    category = "Category ${index % 3}",
//                    rating = 3.5f + (index % 3) * 0.5f,
//                    isAvailable = true
//                )
//            },
//            onProductClick = {}
//        )
//    }
//}


//@PhonePreviews
//@TabletPreviews
//@Composable
//private fun MinimumTouchTargetPreview() {
//    AdaptiveLayoutTheme {
//        // This Box demonstrates how to ensure a minimum touch target size.
//        // The inner Icon is 24x24 dp, which is smaller than the recommended 48x48 dp.
//        // By setting the size of the containing Box to 48x48 dp and making it clickable,
//        // we provide a larger, more accessible touch area around the smaller visual element.
//        Box(
//            modifier = androidx.compose.ui.Modifier
//                .size(48.dp) // Enforce minimum touch target size
//                .clickable { /* Handle click */ },
//            contentAlignment = androidx.compose.ui.Alignment.Center
//        ) {
//            Icon(
//                imageVector = Icons.Default.Info,
//                contentDescription = "Information",
//                modifier = androidx.compose.ui.Modifier.size(24.dp) // Visual size of the icon
//            )
//        }
//    }
//}

@AllDevicePreviews
@Composable
private fun ClickableWithPadding(onClick: () -> Unit) {
    Column {
        ConfusingTraversalOrder()
        Spacer(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black)
        )
        LogicalTraversalOrder()
    }
}

@Composable
fun ConfusingTraversalOrder() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Column 1
        Column(Modifier.weight(1f)) {
            Text("Item A", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            Text("Item B", style = MaterialTheme.typography.headlineMedium)
        }

        // Column 2
        Column(Modifier.weight(1f)) {
            Text("Item C", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            Text("Item D", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
fun LogicalTraversalOrder() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Column 1
        Column(
            Modifier
                .weight(1f)
                .semantics { isTraversalGroup = true } // <-- This is Group 1
        ) {
            Text("Item A", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            Text("Item B", style = MaterialTheme.typography.headlineMedium)
        }

        // Column 2
        Column(
            Modifier
                .weight(1f)
                .semantics { isTraversalGroup = true } // <-- This is Group 2
        ) {
            Text("Item C", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            Text("Item D", style = MaterialTheme.typography.headlineMedium)
        }
    }
}


//@Composable
//fun AbsoluteAlignmenta(modifier: Modifier = Modifier) {
//    val navigator = rememberListDetailPaneScaffoldNavigator<String>(
//        scaffoldDirective = calculatePaneScaffoldDirective(
//            currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)
//        )
//    )
//    NavigableListDetailPaneScaffold(
//        navigator = navigator,
//        listPane = {
//            AnimatedPane {
//                ProductListPane(/* .. */)
//            }
//        },
//        detailPane = {
//            AnimatedPane {
//                ProductDetailPane( /* .. */)
//            }
//        },
//        modifier = modifier
//    )
//}