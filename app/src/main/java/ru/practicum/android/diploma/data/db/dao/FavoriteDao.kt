package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entities.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: FavoriteEntity)
    @Query("SELECT * FROM favorite")
    suspend fun getVacancy(): List<FavoriteEntity>
    @Delete(entity = FavoriteEntity::class)
    suspend fun deleteVacancy(favoriteEntity: FavoriteEntity)
}
