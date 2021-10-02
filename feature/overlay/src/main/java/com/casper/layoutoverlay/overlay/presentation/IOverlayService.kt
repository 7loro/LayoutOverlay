package com.casper.layoutoverlay.overlay.presentation

import com.casper.layoutoverlay.overlay.domain.OverlayItem

interface IOverlayService {
    fun drawLayout(overlayItem: OverlayItem)
    fun removeLayout(overlayItem: OverlayItem)
}