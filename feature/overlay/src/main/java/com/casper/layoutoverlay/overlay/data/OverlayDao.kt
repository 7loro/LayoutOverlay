package com.casper.layoutoverlay.overlay.data

import androidx.room.*
import com.casper.layoutoverlay.overlay.data.model.OverlayEntity

@Dao
interface OverlayDao {

    @Query("SELECT * FROM overlay ORDER BY id DESC")
    suspend fun getAll(): List<OverlayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: OverlayEntity): Long

    @Update
    suspend fun update(item: OverlayEntity): Int

    @Query("DELETE FROM overlay WHERE id=:id")
    suspend fun delete(id: Int): Int

    @Query("DELETE FROM overlay")
    suspend fun deleteAll()
}
