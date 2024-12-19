package ru.practicum.android.diploma.domain.db.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyDbInteractor {
    suspend fun insertVacancy(vacancy: List<VacancyEntity>)
    suspend fun deleteVacancy(vacancy: VacancyEntity)
    suspend fun getVacancy(): Flow<Pair<List<Vacancy>?, String?>>
    suspend fun getVacancyById(vacancyId: String): Flow<VacancyEntity>
    suspend fun getVacancyIds(): Flow<List<String>>
    suspend fun updateVacancy(vacancy: VacancyEntity)
}
