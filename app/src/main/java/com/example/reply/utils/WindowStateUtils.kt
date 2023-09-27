package com.example.reply.utils

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Information about the posture of the device
 */
sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(
        val hingePosition: Rect
    ) : DevicePosture

    data class SeparatingPosture(
        val hingePosition: Rect,
        val orientation: FoldingFeature.Orientation
    ) : DevicePosture
}


@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT &&
            foldFeature.isSeparating
}

/**
 * Different supported navigation types
 */
enum class ReplyNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

enum class ReplyContentType {
    LIST_ONLY, LIST_AND_DETAIL
}