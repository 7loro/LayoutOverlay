package com.casper.layoutoverlay.service.presentation

import com.casper.layoutoverlay.service.domain.OverlayItem

interface OverlayWindow {
    fun open(overlayItem: OverlayItem)
    fun close(overlayItem: OverlayItem)
    fun closeAll()
}
