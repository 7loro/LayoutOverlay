package com.casper.layoutoverlay.overlay.domain

import com.casper.layoutoverlay.overlay.presentation.Shape

data class OverlayItem(
    val id: Int,
    val shape: Shape,
    var width: Int,
    var height: Int
)