package com.example.adaptivelayout.util

/*
 *  DevicePosture.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */


import androidx.compose.material3.adaptive.HingeInfo
import androidx.compose.material3.adaptive.Posture
import androidx.compose.runtime.Composable

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
