package com.casper.layoutoverlay.service.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import com.casper.layoutoverlay.service.R
import com.casper.layoutoverlay.service.domain.OverlayItem

@SuppressLint("ClickableViewAccessibility", "InflateParams")
class OverlayWindowManager(private var context: Context) : OverlayWindow {

    private var params: WindowManager.LayoutParams? = null
    private val windowManager: WindowManager
    private val layoutInflater: LayoutInflater
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0f
    private var initialTouchY: Float = 0f
    private var overlayViewMap = HashMap<Int, OverlayView>()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Display it on top of other application windows
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // Don't let it grab the input focus
                PixelFormat.TRANSLUCENT // Make the underlying application window visible through any transparent parts
            )
        }
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun open(overlayItem: OverlayItem) {
        var overlayView = overlayViewMap[overlayItem.id]
        if (overlayView == null || (overlayView.windowToken == null && overlayView.parent == null)) {
            overlayView = inflateView(overlayItem)
        }
        overlayView?.apply {
            updateSize(overlayItem.width, overlayItem.height)
            windowManager.updateViewLayout(this, layoutParams)
        }
    }

    private fun inflateView(overlayItem: OverlayItem): OverlayView? {
        return runCatching {
            val inflatedView = when (overlayItem.shape) {
                Shape.Rect -> {
                    layoutInflater.inflate(R.layout.rect, null) as OverlayView
                }
                Shape.Square -> {
                    // TODO support square
                    layoutInflater.inflate(R.layout.rect, null) as OverlayView
                }
                Shape.Circle -> {
                    // TODO support circle
                    layoutInflater.inflate(R.layout.rect, null) as OverlayView
                }
            }
            inflatedView.setOnTouchListener { view, event ->
                // Move window position as drag
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params?.x ?: 0
                        initialY = params?.y ?: 0
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params?.x = initialX + (event.rawX - initialTouchX).toInt()
                        params?.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager.updateViewLayout(view, params)
                    }
                }
                false
            }
            overlayViewMap[overlayItem.id] = inflatedView
            windowManager.addView(inflatedView, params)
            inflatedView
        }.onFailure {
            Log.e(TAG, "inflateView Error ${it.message}")
        }.getOrNull()
    }

    override fun close(overlayItem: OverlayItem) {
        val windowManager = (context.getSystemService(WINDOW_SERVICE) as WindowManager)
        runCatching {
            overlayViewMap[overlayItem.id]?.apply {
                windowManager.removeView(this)
                invalidate()
                (parent as ViewGroup).removeAllViews()
            }
        }.onFailure {
            Log.e(TAG, "close $overlayItem Error ${it.message}")
        }
    }

    override fun closeAll() {
        val windowManager = (context.getSystemService(WINDOW_SERVICE) as WindowManager)
        runCatching {
            overlayViewMap.forEach {
                val overlayView = it.value
                windowManager.removeView(overlayView)
                overlayView.invalidate()
                (overlayView.parent as ViewGroup).removeAllViews()
            }
        }.onFailure {
            Log.e(TAG, "closeAll Error ${it.message}")
        }
    }

    private companion object {
        private const val TAG = "OverlayWindowManager"
    }
}
