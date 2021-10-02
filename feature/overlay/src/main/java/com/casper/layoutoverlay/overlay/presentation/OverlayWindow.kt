package com.casper.layoutoverlay.overlay.presentation

import com.casper.layoutoverlay.overlay.domain.OverlayItem

interface OverlayWindow {
    fun open(overlayItem: OverlayItem)
    fun close(overlayItem: OverlayItem)
    fun closeAll()
}