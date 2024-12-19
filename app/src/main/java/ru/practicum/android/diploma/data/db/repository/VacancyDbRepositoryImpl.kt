package ru.practicum.android.diploma.data.db.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.AppDataBase
import ru.practicum.android.diploma.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter
) : VacancyDbRepository {
    override suspend fun insertVacancy(vacancy: List<VacancyEntity>) {
        appDataBase.vacancyDao().insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: VacancyEntity) {
        appDataBase.vacancyDao().deleteVacancy(vacancy)
    }

    override suspend fun getVacancy(): Flow<List<Vacancy>> = flow {
        val vacancies = appDataBase.vacancyDao().getVacancy()
        emit(convertFromVacancyEntity(vacancies))
    }

    override suspend fun getVacancyById(vacancyId: String): Flow<VacancyEntity> = flow {
        val vacancies = appDataBase.vacancyDao().getVacancyById(vacancyId)
        emit(vacancies)
    }

    override suspend fun getVacancyIds(): Flow<List<String>> = flow {
        val vacancies = appDataBase.vacancyDao().getVacancyIds()
        emit(vacancies)
    }

    override suspend fun updateVacancy(vacancy: VacancyEntity) {
        appDataBase.vacancyDao().updateVacancyEntity(vacancy)
    }

    private fun convertFromVacancyEntity(vacancies: List<VacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancy -> vacancyDbConverter.map(vacancy) }
    }
}
