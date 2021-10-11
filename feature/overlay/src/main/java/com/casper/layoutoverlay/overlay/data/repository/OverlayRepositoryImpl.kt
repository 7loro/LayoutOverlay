package com.casper.layoutoverlay.overlay.data.repository

import com.casper.layoutoverlay.overlay.data.OverlayDao
import com.casper.layoutoverlay.overlay.data.model.toDomainModel
import com.casper.layoutoverlay.overlay.data.model.toEntity
import com.casper.layoutoverlay.overlay.domain.model.Overlay
import com.casper.layoutoverlay.overlay.domain.repository.OverlayRepository
import javax.inject.Inject

class OverlayRepositoryImpl @Inject constructor(
    private val dao: OverlayDao
) : OverlayRepository {

    override suspend fun getOverlayList(): List<Overlay> {
        return dao.getAll().map { it.toDomainModel() }
    }

    override suspend fun addOverlayItem(item: Overlay): Long {
        return dao.insert(item.toEntity())
    }

    override suspend fun updateOverlayItem(item: Overlay): Int {
        return dao.update(item.toEntity())
    }

    override suspend fun deleteOverlayItem(id: Int): Int {
        return dao.delete(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}
