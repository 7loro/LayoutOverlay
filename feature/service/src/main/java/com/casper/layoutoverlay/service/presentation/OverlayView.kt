package com.casper.layoutoverlay.service.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.RelativeLayout
import androidx.core.view.GestureDetectorCompat
import com.casper.layoutoverlay.shared.ktx.startMainActivity

class OverlayView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private val gestureListener: GestureDetector.SimpleOnGestureListener =
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                context.startMainActivity()
                return true
            }
        }

    private var gestureDetector: GestureDetectorCompat =
        GestureDetectorCompat(context, gestureListener)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    fun updateSize(width: Int, height: Int) {
        val param = layoutParams
        param.width = width
        param.height = height
        layoutParams = param
    }
}
