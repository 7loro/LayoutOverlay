package com.casper.layoutoverlay.overlay.presentation

sealed class Shape() {
    object Rect: Shape()
    object Square: Shape()
    object Circle: Shape()
}
