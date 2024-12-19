package ru.practicum.android.diploma

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavoriteDao
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.db.entities.FavoriteEntity
import ru.practicum.android.diploma.data.db.entities.VacancyEntity

@Database(version = 2, entities = [FavoriteEntity::class, VacancyEntity::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun vacancyDao(): VacancyDao
}
