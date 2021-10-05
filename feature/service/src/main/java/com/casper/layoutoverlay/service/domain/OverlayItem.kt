package com.casper.layoutoverlay.service.domain

import com.casper.layoutoverlay.service.presentation.Shape

data class OverlayItem(
    val id: Int,
    val shape: Shape,
    var widthPx: Int,
    var heightPx: Int
)