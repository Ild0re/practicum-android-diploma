package ru.practicum.android.diploma.data.db.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.AppDataBase
import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.mappers.toVacancy
import ru.practicum.android.diploma.util.mappers.toVacancyEntity
import java.sql.SQLException

class VacancyDbRepositoryImpl(
    private val appDataBase: AppDataBase,
) : VacancyDbRepository {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        appDataBase.vacancyDao().insertVacancy(convertFromVacancyToEntity(vacancy))
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        appDataBase.vacancyDao().deleteVacancy(convertFromVacancyToEntity(vacancy))
    }

    override suspend fun getVacancy(): Flow<Resource<List<Vacancy>>> = flow {
        try {
            val vacancies = appDataBase.vacancyDao().getVacancy()
            emit(Resource.Success(convertFromVacancyEntity(vacancies)))
        } catch (e: SQLException) {
            emit(Resource.Error(e.message!!))
        }
    }
    override suspend fun getVacancyById(vacancyId: String): Flow<Vacancy> = flow {
        val vacancy = appDataBase.vacancyDao().getVacancyById(vacancyId)
        emit(convertFromEntityToVacancy(vacancy))
    }
    override suspend fun getVacancyIds(): Flow<List<String>> = flow {
        val vacancies = appDataBase.vacancyDao().getVacancyIds()
        emit(vacancies)
    }
    override suspend fun updateVacancy(vacancy: VacancyEntity) {
        appDataBase.vacancyDao().updateVacancyEntity(vacancy)
    }
    private fun convertFromVacancyEntity(vacancies: List<VacancyEntity>): List<Vacancy> {
        return vacancies.map { it.toVacancy() }
    }

    private fun convertFromVacancyToEntity(vacancy: Vacancy): VacancyEntity {
        return vacancy.toVacancyEntity()
    }

    private fun convertFromEntityToVacancy(vacancy: VacancyEntity): Vacancy {
        return vacancy.toVacancy()
    }
}
