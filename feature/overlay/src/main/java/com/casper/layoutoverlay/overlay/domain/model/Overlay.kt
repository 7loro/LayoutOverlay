package com.casper.layoutoverlay.overlay.domain.model

import android.content.Context
import android.util.TypedValue
import com.casper.layoutoverlay.service.domain.OverlayItem
import com.casper.layoutoverlay.service.presentation.Shape

data class Overlay(
    val id: Int? = null,
    val shape: Shape,
    var widthDp: Int,
    var heightDp: Int
)

internal fun Overlay.toServiceModel(context: Context): OverlayItem {
    val widthPx = dpToPx(context, this.widthDp)
    val heightPx = dpToPx(context, this.heightDp)
    return this.id?.let { id ->
        OverlayItem(id, this.shape, widthPx, heightPx)
    } ?: run {
        // TODO support multiple id
        OverlayItem(0, this.shape, widthPx, heightPx)
    }
}

private fun dpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}