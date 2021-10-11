package com.casper.layoutoverlay.overlay.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
