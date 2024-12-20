package ru.practicum.android.diploma.domain.db.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.data.db.repository.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class VacancyDbInteractorImpl(private val repository: VacancyDbRepository) : VacancyDbInteractor {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        return repository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        return repository.deleteVacancy(vacancy)
    }

    override suspend fun getVacancy(): Flow<Pair<List<Vacancy>?, String?>> {
        return repository.getVacancy().map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }

    override suspend fun getVacancyById(vacancyId: String): Flow<Vacancy> {
        return repository.getVacancyById(vacancyId)
    }

    override suspend fun getVacancyIds(): Flow<List<String>> {
        return repository.getVacancyIds()
    }

    override suspend fun updateVacancy(vacancy: VacancyEntity) {
        return repository.updateVacancy(vacancy)
    }
}
