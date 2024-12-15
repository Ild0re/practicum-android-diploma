package ru.practicum.android.diploma

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavoriteDao
import ru.practicum.android.diploma.data.db.entities.FavoriteEntity

@Database(version = 1, entities = [FavoriteEntity::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
