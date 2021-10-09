package com.casper.layoutoverlay.service.presentation

import com.casper.layoutoverlay.service.domain.OverlayItem

interface IOverlayService {
    fun drawLayout(overlayItem: OverlayItem)
    fun removeLayout(overlayItem: OverlayItem)
}