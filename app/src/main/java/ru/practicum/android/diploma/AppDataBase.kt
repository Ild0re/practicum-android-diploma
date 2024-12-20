package ru.practicum.android.diploma

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.db.entities.VacancyEntity

@Database(version = 2, entities = [VacancyEntity::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
