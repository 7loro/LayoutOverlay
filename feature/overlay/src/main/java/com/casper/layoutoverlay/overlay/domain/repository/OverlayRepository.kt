package com.casper.layoutoverlay.overlay.domain.repository

import com.casper.layoutoverlay.overlay.domain.model.Overlay

interface OverlayRepository {

    suspend fun getOverlayList(): List<Overlay>
    suspend fun addOverlayItem(item: Overlay): Long
    suspend fun updateOverlayItem(item: Overlay): Int
    suspend fun deleteOverlayItem(id: Int): Int
    suspend fun deleteAll()
}