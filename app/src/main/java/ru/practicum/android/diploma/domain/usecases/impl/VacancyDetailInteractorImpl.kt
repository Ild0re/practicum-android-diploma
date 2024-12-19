package ru.practicum.android.diploma.domain.usecases.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.domain.usecases.base.VacancyDetailInteractor
import ru.practicum.android.diploma.util.Resource

class VacancyDetailInteractorImpl(private val repository: VacancyRepository) : VacancyDetailInteractor {
    override suspend fun getVacancyById(id: String): Flow<Pair<Vacancy?, String?>> {
        return repository.getVacancyById(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
