package com.casper.layoutoverlay.overlay.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.casper.layoutoverlay.overlay.data.model.OverlayEntity
import com.casper.layoutoverlay.overlay.data.model.ShapeConverter

@Database(entities = [OverlayEntity::class], version = 1, exportSchema = false)
@TypeConverters(ShapeConverter::class)
abstract class OverlayDatabase : RoomDatabase() {

    abstract fun overlayDao(): OverlayDao
}
