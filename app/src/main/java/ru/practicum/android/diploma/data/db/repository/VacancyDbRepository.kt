package ru.practicum.android.diploma.data.db.repository

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import kotlinx.coroutines.flow.Flow

interface VacancyDbRepository {
    suspend fun insertVacancy(vacancy: List<VacancyEntity>)
    suspend fun deleteVacancy(vacancy: VacancyEntity)
    suspend fun getVacancy(): Flow<List<Vacancy>>
    suspend fun getVacancyById(vacancyId: String): Flow<VacancyEntity>
    suspend fun updateVacancy(vacancy: VacancyEntity)
}
