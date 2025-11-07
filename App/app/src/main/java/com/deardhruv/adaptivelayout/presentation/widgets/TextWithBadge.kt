package com.deardhruv.adaptivelayout.presentation.widgets

/*
 *  TextWithBadge.kt
 *
 *  Created by Dhruv Patel on 07/11/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Display a block of text, with an icon appearing in the last line if there is enough space.
 * Otherwise, display the icon on its own line, at the end.
 *
 * +----------------+
 * | Lorem ipsum    |
 * | dolor (✔️)     |
 * +----------------+
 *
 * +----------------+
 * | Lorem ipsum    |
 * | dolor sit amet |
 * |           (✔️) |
 * +----------------+
 */
@Preview(widthDp = 200, heightDp = 200)
@Preview(widthDp = 300, heightDp = 200)
@Preview(widthDp = 300, heightDp = 400)
@Preview(widthDp = 300, heightDp = 400, locale = "ar")
@Composable
fun TextWithBadgeEnd() {
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

    Surface(modifier = Modifier.wrapContentSize()) {
        var textLayoutResult: TextLayoutResult? by remember { mutableStateOf(null) }

        Layout(
            content = {
                Text(
                    text,
                    onTextLayout = {
                        textLayoutResult = it
                    },
                    modifier = Modifier.layoutId("text")
                )

                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Received",
                    modifier = Modifier.layoutId("badge").size(24.dp)
                )
            },
            measurePolicy = { measurables, constraints ->
                val textPlaceable = measurables.find { it.layoutId == "text" }!!.measure(constraints)
                val badgePlaceable = measurables.find { it.layoutId == "badge" }!!.measure(constraints)
                val badgeWidth = badgePlaceable.width
                val badgeHeight = badgePlaceable.height

                val newTextLayoutResult = textLayoutResult!!

                val lastLineEndBoundingBox =
                    newTextLayoutResult.getBoundingBox(
                        newTextLayoutResult.getLineEnd(
                            newTextLayoutResult.lineCount - 1, visibleEnd = true
                        ) - 1
                    )
                val lastLineRelativeOffset = IntOffset(
                    when (layoutDirection) {
                        LayoutDirection.Ltr ->
                            lastLineEndBoundingBox.right
                        LayoutDirection.Rtl ->
                            newTextLayoutResult.size.width - lastLineEndBoundingBox.left
                    }.roundToInt(),
                    lastLineEndBoundingBox.top.roundToInt(),
                )

                val displayBadgeInLastLine =
                    constraints.maxWidth - lastLineRelativeOffset.x >= badgeWidth

                val width: Int
                val height: Int
                val badgeX: Int
                val badgeY: Int

                if (displayBadgeInLastLine) {
                    width = max(
                        newTextLayoutResult.size.width,
                        lastLineRelativeOffset.x + badgeWidth)
                    height = max(
                        newTextLayoutResult.size.height,
                        lastLineRelativeOffset.y + badgeHeight
                    )
                    badgeX = lastLineRelativeOffset.x
                    badgeY = lastLineRelativeOffset.y
                } else {
                    width = max(newTextLayoutResult.size.width, badgeWidth)
                    height = newTextLayoutResult.size.height + badgeHeight
                    badgeX = max(0, newTextLayoutResult.size.width - badgeWidth)
                    badgeY = newTextLayoutResult.size.height
                }

                layout(width, height) {
                    textPlaceable.placeRelative(0, 0)
                    badgePlaceable.placeRelative(badgeX, badgeY)
                }
            },
        )
    }
}