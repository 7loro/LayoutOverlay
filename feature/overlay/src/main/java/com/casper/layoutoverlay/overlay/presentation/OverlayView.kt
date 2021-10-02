package com.casper.layoutoverlay.overlay.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class OverlayView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    fun updateSize(width: Int, height: Int) {
        val param = layoutParams
        param.width = width
        param.height = height
        layoutParams = param
    }
}
