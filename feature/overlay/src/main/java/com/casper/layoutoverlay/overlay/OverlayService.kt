package com.casper.layoutoverlay.overlay

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_CHANNEL = "com.casper.layoutoverlay.overlayservice.noti"
private const val NOTIFICATION_CHANNEL_NAME = ""

class OverlayService : Service() {
    private val binder = OverlayServiceBinder()

    private var overlayWindow: OverlayWindow? = null

    inner class OverlayServiceBinder : Binder() {
        fun getService(): OverlayService = this@OverlayService
    }

    override fun onBind(intent: Intent): IBinder {
        // create an instance of Window class
        // and display the content on screen
        overlayWindow = OverlayWindow(this)
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        // create the custom or default notification
        // based on the android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyOwnForeground()
        } else {
            startForeground(1, Notification())
        }
    }

    fun drawLayout(width: Int, height: Int) {
        overlayWindow?.open(width, height)
    }

    override fun onDestroy() {
        super.onDestroy()
        overlayWindow?.close()
        stopSelf()
    }

    // for android version >=O we need to create
    // custom notification stating foreground service is running
    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {
        val channelName = "Background Service"
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    channelName,
                    NotificationManager.IMPORTANCE_MIN
                )
            )
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Service running")
            .setContentText("Displaying over other apps")
            // this is important, otherwise the notification will show the way
            // you want i.e. it will show some default notification
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }
}