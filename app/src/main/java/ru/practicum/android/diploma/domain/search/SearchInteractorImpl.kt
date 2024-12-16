package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(private val repository: SearchRepository): SearchInteractor {
    override suspend fun getAllVacancies(expression: String, page: Int): Flow<Pair<List<VacancyDto>?, String?>> {
        // заменить VacancyDto на модель Vacancy
        return repository.getAllVacancies(expression, page).map { result ->
            when(result) {
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
