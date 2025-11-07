package com.deardhruv.adaptivelayout.presentation.widgets

/*
 * MoreButtonText.kt
 *
 * Created by Dhruv Patel on 07/11/2023.
 * Copyright © 2023 DearDhruv. All rights reserved.
 */

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.round
import kotlin.math.max
import kotlin.text.Typography.ellipsis

/**
 * Display a block of text with a max number of lines.
 *
 * If there is enough space to display all of the text, display it.
 * Otherwise display a button after the text to display more.
 *
 * +----------------+
 * | Lorem ipsum    |
 * | dolor...  (+) |
 * +----------------+
 *
 * +----------------+
 * | Lorem ipsum    |
 * | dolor sit amet |
 * +----------------+
 */
@Preview(widthDp = 200, heightDp = 300)
@Preview(widthDp = 300, heightDp = 300)
@Preview(widthDp = 400, heightDp = 300)
@Preview(widthDp = 500, heightDp = 300)
@Preview(widthDp = 600, heightDp = 300)
@Composable
fun MoreButtonTextEnd() {
    val maxLines = 5
    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    val textMeasurer = rememberTextMeasurer()

    var showButton by remember { mutableStateOf(false) }
    var ellipsizedLayoutResult: TextLayoutResult? by remember { mutableStateOf(null) }

    Surface(Modifier.wrapContentSize()) {
        val contentColor = LocalContentColor.current
        val style = LocalTextStyle.current
        Layout(
            content = {
                Text(
                    text = text,
                    style = style,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = maxLines,
                    modifier = Modifier
                        .layoutId("text")
                        .drawBehind {
                            ellipsizedLayoutResult?.let {
                                drawText(
                                    it,
                                    contentColor,
                                )
                            }
                        },
                    onTextLayout = {
                        // This is the layout result of the full text. This is no longer
                        // needed and can be removed.
                    },
                    // Text will be rendered in the modifier, but keep the full text for
                    // semantics
                    color = Color.Transparent
                )

                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.layoutId("button")
                ) {
                    Text("More")
                }
            },
            measurePolicy = { measurables, constraints ->
                val textMeasurable = measurables.find { it.layoutId == "text" }
                val textPlaceable = textMeasurable!!.measure(constraints)

                val fullTextLayoutResult = textMeasurer.measure(
                    text = text,
                    style = style,
                    constraints = constraints,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis
                )

                val buttonMeasurable = measurables.find { it.layoutId == "button" }
                val buttonPlaceable = buttonMeasurable!!.measure(constraints)

                // We need to show the button if the last line was ellipsized
                showButton = fullTextLayoutResult.isLineEllipsized(fullTextLayoutResult.lineCount - 1)

                if (showButton) {
                    val ellipsisTextLayoutResult = textMeasurer.measure(
                        ellipsis.toString(),
                        style = style,
                    )
                    // Determine how much space is available for the last line of text without
                    // the ellipsis
                    val lastLineTextWidth = constraints.maxWidth -
                            buttonPlaceable.width -
                            ellipsisTextLayoutResult.size.width

                    val lastCharOffset = fullTextLayoutResult.getOffsetForPosition(
                        Offset(
                            lastLineTextWidth.toFloat(),
                            fullTextLayoutResult.getLineBottom(maxLines - 1)
                        )
                    ) - 1

                    ellipsizedLayoutResult = textMeasurer.measure(
                        text.substring(0, lastCharOffset) + ellipsis,
                        style = style,
                        constraints = constraints
                    )

                    val lastLineEndBoundingBox = ellipsizedLayoutResult!!.getBoundingBox(
                        ellipsizedLayoutResult!!.getLineEnd(
                            ellipsizedLayoutResult!!.lineCount - 1,
                            visibleEnd = true
                        ) - 1
                    )

                    val lastLineRelativeOffset = Offset(
                        when (layoutDirection) {
                            LayoutDirection.Ltr ->
                                lastLineEndBoundingBox.right

                            LayoutDirection.Rtl ->
                                ellipsizedLayoutResult!!.size.width - lastLineEndBoundingBox.left
                        },
                        lastLineEndBoundingBox.top,
                    ).round()

                    val width = constraints.maxWidth
                    val height = max(
                        fullTextLayoutResult.size.height,
                        lastLineRelativeOffset.y + buttonPlaceable.height
                    )
                    val buttonX = constraints.maxWidth - buttonPlaceable.width
                    val buttonY = lastLineRelativeOffset.y

                    layout(width, height) {
                        textPlaceable.placeRelative(0, 0)
                        buttonPlaceable.placeRelative(buttonX, buttonY)
                    }
                } else {
                    layout(fullTextLayoutResult.size.width, fullTextLayoutResult.size.height) {
                        textPlaceable.placeRelative(0, 0)
                    }
                }
            },
        )
    }
}