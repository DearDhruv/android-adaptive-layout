package com.deardhruv.adaptivelayout.presentation.widgets

/*
 *  AdaptiveButtons.kt
 *
 *  Created by Dhruv Patel on 07/11/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.max


/**
 * https://gist.github.com/alexvanyo/d246ce358cbe63488f167bcf3357a77f
 * Show two buttons:
 * If there is room to show both when each fill up 50% of the available width, show them
 * horizontally side-by-side with each having 50% of the width.
 *
 * Otherwise, show them filling up the entire width on top of each other
 *
 * +-----------------------------+
 * |                             |
 * | ( Secondary ) (  Primary  ) |
 * |                             |
 * +-----------------------------+
 *
 * +----------------------------+
 * |                            |
 * | (         Primary        ) |
 * | (        Secondary       ) |
 * |                            |
 * +----------------------------+
 */
@Preview(widthDp = 100, heightDp = 300)
@Preview(widthDp = 300, heightDp = 600)
@Composable
fun AdaptiveButtonsEnd() {
    Layout(
        content = {
            Button(
                onClick = { },
                modifier = Modifier
                    .layoutId("primary")
                    .heightIn(min = 48.dp)
                    .widthIn(min = 48.dp)
            ) {
                Text("Primary")
            }
            Button(
                onClick = { },
                modifier = Modifier
                    .layoutId("secondary")
                    .heightIn(min = 48.dp)
                    .widthIn(min = 48.dp)
            ) {
                Text("Secondary")
            }
        },
        measurePolicy = { measurables, constraints ->
            val primaryButtonMeasurable = measurables.find { it.layoutId == "primary" }!!
            val secondaryButtonMeasurable = measurables.find { it.layoutId == "secondary" }!!

            val primaryButtonMinIntrinsicWidth =
                primaryButtonMeasurable.minIntrinsicWidth(constraints.maxHeight)
            val secondaryButtonMinIntrinsicWidth =
                secondaryButtonMeasurable.minIntrinsicWidth(constraints.maxHeight)

            val showHorizontally = primaryButtonMinIntrinsicWidth <= constraints.maxWidth / 2 &&
                    secondaryButtonMinIntrinsicWidth <= constraints.maxWidth / 2

            val width = constraints.minWidth
            val height: Int
            val primaryButtonPlaceable: Placeable
            val secondaryButtonPlaceable: Placeable

            if (showHorizontally) {
                val halfWidthConstraints = constraints.copy(
                    minWidth = constraints.maxWidth / 2,
                    maxWidth = constraints.maxWidth / 2
                )

                primaryButtonPlaceable = primaryButtonMeasurable.measure(halfWidthConstraints)
                secondaryButtonPlaceable = secondaryButtonMeasurable.measure(halfWidthConstraints)

                height = max(primaryButtonPlaceable.height, secondaryButtonPlaceable.height)
            } else {
                val fullWidthConstraints = constraints.copy(
                    minWidth = constraints.maxWidth,
                )
                primaryButtonPlaceable = primaryButtonMeasurable.measure(fullWidthConstraints)
                secondaryButtonPlaceable = secondaryButtonMeasurable.measure(fullWidthConstraints)

                height = primaryButtonPlaceable.height + secondaryButtonPlaceable.height
            }

            layout(width, height) {
                if (showHorizontally) {
                    primaryButtonPlaceable.placeRelative(
                        width / 2,
                        (height - primaryButtonPlaceable.height) / 2
                    )
                    secondaryButtonPlaceable.placeRelative(
                        0,
                        (height - secondaryButtonPlaceable.height) / 2
                    )
                } else {
                    primaryButtonPlaceable.placeRelative(
                        0,
                        0
                    )
                    secondaryButtonPlaceable.placeRelative(
                        0,
                        primaryButtonPlaceable.height
                    )
                }
            }
        },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    )
}