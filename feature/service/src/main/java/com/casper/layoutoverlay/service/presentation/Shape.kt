package com.casper.layoutoverlay.service.presentation

sealed class Shape {
    object Rect : Shape()
    object Square : Shape()
    object Circle : Shape()
}
