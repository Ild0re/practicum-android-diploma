package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.practicum.android.diploma.data.db.entities.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)
    @Query("SELECT * FROM vacancy_table")
    suspend fun getVacancy(): List<VacancyEntity>
    @Query("SELECT * FROM vacancy_table WHERE id LIKE :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyEntity
    @Query("SELECT id FROM vacancy_table")
    suspend fun getVacancyIds(): List<String>
    @Delete(entity = VacancyEntity::class)
    suspend fun deleteVacancy(vacancyEntity: VacancyEntity)
    @Update(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateVacancyEntity(vacancyEntity: VacancyEntity)
}
