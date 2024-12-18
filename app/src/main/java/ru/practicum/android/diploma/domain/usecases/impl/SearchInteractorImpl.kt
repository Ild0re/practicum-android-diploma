package ru.practicum.android.diploma.domain.usecases.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.domain.usecases.base.SearchInteractor
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(private val repository: VacancyRepository) : SearchInteractor {
    override suspend fun getAllVacancies(expression: String, page: Int): Flow<Pair<VacancyList?, String?>> {
        return repository.getAllVacancies(expression, page).map { result ->
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
