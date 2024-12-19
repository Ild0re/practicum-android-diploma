package ru.practicum.android.diploma.domain.db.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbInteractorImpl(private val repository: VacancyDbRepository) : VacancyDbInteractor {
    override suspend fun insertVacancy(vacancy: List<VacancyEntity>) {
        return repository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: VacancyEntity) {
        return repository.deleteVacancy(vacancy)
    }

    override suspend fun getVacancy(): Flow<List<Vacancy>> {
        return repository.getVacancy()
    }

    override suspend fun getVacancyById(vacancyId: String): Flow<VacancyEntity> {
        return repository.getVacancyById(vacancyId)
    }

    override suspend fun getVacancyIds(): Flow<List<String>> {
        return repository.getVacancyIds()
    }

    override suspend fun updateVacancy(vacancy: VacancyEntity) {
        return repository.updateVacancy(vacancy)
    }
}
