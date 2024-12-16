package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val hhApi: HhApi,
) : SearchRepository {

    companion object {
        private const val VACANCIES_PER_PAGE = 20
    }

    override suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<List<VacancyDto>>> = flow {
        // заменить VacancyDto на модель Vacancy
        val pages: HashMap<String, Int> = HashMap()
        val options: HashMap<String, String> = HashMap()

        options["text"] = expression
        pages["page"] = page
        pages["per_page"] = VACANCIES_PER_PAGE

        val response = networkClient.executeRequest {
            hhApi.getAllVacancies(pages, options)
        }

        if (response.isSuccess) {
            val vacancies = response.getOrNull()?.items
            if (vacancies.isNullOrEmpty()) {
                emit(Resource.Error("Не удалось получить список вакансий"))
            } else {
                emit(Resource.Success(vacancies))
            }
        } else {
            val exception = response.exceptionOrNull()?.message!!
            if (exception == "timeout") {
                emit(Resource.Error("Нет интернета"))
            } else {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}
