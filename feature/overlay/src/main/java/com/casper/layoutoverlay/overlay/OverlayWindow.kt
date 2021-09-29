package com.casper.layoutoverlay.overlay

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*

@SuppressLint("ClickableViewAccessibility", "InflateParams")
class OverlayWindow(private var context: Context) {
    // declaring required variables
    private val overlayContainer: View
    private var params: WindowManager.LayoutParams? = null
    private val windowManager: WindowManager
    private val layoutInflater: LayoutInflater
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0f
    private var initialTouchY: Float = 0f

    fun open(width: Int, height: Int) {
        try {
            // check if the view is already inflated or present in the window
            if (overlayContainer.windowToken == null) {
                if (overlayContainer.parent == null) {
                    windowManager.addView(overlayContainer, params)
                }
            }
            updateOverlayView(width, height)
        } catch (e: Exception) {
            Log.d("Error1", e.toString())
        }
    }

    fun close() {
        try {
            (context.getSystemService(WINDOW_SERVICE) as WindowManager).removeView(overlayContainer)
            overlayContainer.invalidate()
            (overlayContainer.parent as ViewGroup).removeAllViews()
            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (e: Exception) {
            Log.d("Error2", e.toString())
        }
    }

    private fun updateOverlayView(width: Int, height: Int) {
        val param = overlayContainer.layoutParams
        param.width = width
        param.height = height
        overlayContainer.layoutParams = param
    }

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
        overlayContainer = layoutInflater.inflate(R.layout.popup, null)
        overlayContainer.setOnTouchListener { view, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params?.x ?: 0
                    initialY = params?.y ?: 0
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    params?.x = initialX + (event.rawX - initialTouchX).toInt()
                    params?.y = initialY + (event.rawY - initialTouchY).toInt()
                    windowManager.updateViewLayout(overlayContainer, params)
                }
            }
            true
        }
    }
}