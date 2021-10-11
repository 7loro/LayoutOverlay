package com.casper.layoutoverlay.overlay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.casper.layoutoverlay.overlay.domain.model.Overlay
import com.casper.layoutoverlay.service.presentation.Shape
import org.json.JSONObject

@Entity(tableName = "overlay")
data class OverlayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val shape: Shape,
    var width: Int,
    var height: Int
)

internal fun OverlayEntity.toDomainModel() = Overlay(this.id, this.shape, this.width, this.height)

internal fun Overlay.toEntity(): OverlayEntity {
    return this.id?.let { id ->
        OverlayEntity(id = id, shape = this.shape, width = this.widthDp, height = this.heightDp)
    } ?: run {
        OverlayEntity(shape = this.shape, width = this.widthDp, height = this.heightDp)
    }
}

class ShapeConverter {
    @TypeConverter
    fun fromShape(shape: Shape): String {
        return JSONObject().apply {
            put("shape", shape.javaClass.simpleName)
        }.toString()
    }

    @TypeConverter
    fun toShape(shape: String): Shape {
        val json = JSONObject(shape)
        return when (json.getString("shape")) {
            "Rect" -> Shape.Rect
            "Square" -> Shape.Square
            "Circle" -> Shape.Circle
            else -> { throw UnsupportedOperationException("Not support shape") }
        }
    }
}
