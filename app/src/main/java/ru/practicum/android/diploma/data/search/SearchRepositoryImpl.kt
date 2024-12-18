package ru.practicum.android.diploma.data.search

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.VacancyResponse
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

    override suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<VacancyResponse>> = flow {
        // заменить VacancyDto на модель Vacancy
        val pages: HashMap<String, Int> = HashMap()
        val options: HashMap<String, String> = HashMap()

        options["text"] = expression
        options["search_field"] = "name"
        pages["page"] = page
        pages["per_page"] = VACANCIES_PER_PAGE

        val response = networkClient.executeRequest {
            hhApi.getAllVacancies(pages, options)
        }

        if (response.isSuccess) {
            if (response.getOrNull() == null) {
                emit(Resource.Error("Не удалось получить список вакансий"))
            } else {
                emit(Resource.Success(response.getOrNull()!!))
            }
        } else {
            if (response.exceptionOrNull()?.message == null) {
                emit(Resource.Error("Нет интернета"))
            } else {
                val exception = response.exceptionOrNull()!!.message
                when (exception) {
                    "Нет интернета" -> emit(Resource.Error("Нет интернета"))
                    "timeout" -> emit(Resource.Error("Нет интернета"))
                    "bad_argument" -> emit(Resource.Error("Не удалось получить список вакансий"))
                    else -> emit(Resource.Error("Ошибка сервера"))
                }
            }
        }
    }
}
