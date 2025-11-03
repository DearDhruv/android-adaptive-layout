@file:Suppress("UNCHECKED_CAST")

package com.deardhruv.adaptivelayout.util

/*
 *  DevicePosture.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import android.app.Activity
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.HingeInfo
import androidx.compose.material3.adaptive.Posture
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.flow.map

/**
 * Represents different device postures for foldables
 */
enum class DevicePostureType {
    NORMAL,
    TABLETOP,  // Half-opened horizontal (video watching)
    BOOK       // Half-opened vertical (reading)
}

/**
 * Detects the current device posture
 */

@Composable
fun detectDevicePosture(windowPosture: Posture): DevicePostureType {
    return when {
        windowPosture.isTabletop -> DevicePostureType.TABLETOP
        windowPosture.isSeparating -> DevicePostureType.BOOK
        else -> DevicePostureType.NORMAL
    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun rememberDevicePosture(): State<Posture> {
    val activity = LocalContext.current as Activity

    // State for posture, collected from WindowInfoTracker
    val postureState: State<Posture> = produceState(initialValue = Posture()) {
        WindowInfoTracker.getOrCreate(activity).windowLayoutInfo(activity)
            .map { layoutInfo ->
                // Correctly use calculatePosture to derive the posture
                calculatePosture(layoutInfo.displayFeatures as List<FoldingFeature>)
            }
            .collect { value = it }
    }
    return postureState

}


fun calculatePosture(foldingFeatures: List<FoldingFeature>): Posture {
    var isTableTop = false
    val hingeList = mutableListOf<HingeInfo>()
    @Suppress("ListIterator")
    foldingFeatures.forEach {
        if (
            it.orientation == FoldingFeature.Orientation.HORIZONTAL &&
            it.state == FoldingFeature.State.HALF_OPENED
        ) {
            isTableTop = true
        }
        hingeList.add(
            HingeInfo(
                bounds = it.bounds.toComposeRect(),
                isFlat = it.state == FoldingFeature.State.FLAT,
                isVertical = it.orientation == FoldingFeature.Orientation.VERTICAL,
                isSeparating = it.isSeparating,
                isOccluding = it.occlusionType == FoldingFeature.OcclusionType.FULL,
            )
        )
    }
    return Posture(isTableTop, hingeList)
}

/**
 * Extension properties for Posture detection
 */
val Posture.isTabletop: Boolean
    get() = hingeList.any { hinge ->
        hinge.isHorizontal && hinge.isHalfOpened
    }

val Posture.isSeparating: Boolean
    get() = hingeList.any { hinge ->
        hinge.isVertical && hinge.isSeparating
    }

/**
 * Extension properties for HingeInfo
 */
val HingeInfo.isHorizontal: Boolean
    get() = !isVertical

val HingeInfo.isVertical: Boolean
    get() = bounds.width < bounds.height

val HingeInfo.isHalfOpened: Boolean
    get() = !isFlatOpened

val HingeInfo.isFlatOpened: Boolean
    get() = isFlatOpened
