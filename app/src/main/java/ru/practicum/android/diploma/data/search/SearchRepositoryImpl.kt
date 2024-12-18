package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val hhApi: HhApi,
) : SearchRepository {

    companion object {
        private const val VACANCIES_PER_PAGE = 20
    }

    override suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<VacancyList>> = flow {
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

                val vacancyList = VacancyList(
                    item = response.getOrNull()!!.items.map {
                        Vacancy(
                            it.id,
                            it.name,
                            it.area.name,
                            it.employer.name,
                            it.employer.logoUrls?.original ?: "",
                            it.url,
                            it.salary?.from.toString(),
                            it.salary?.to.toString(),
                            it.salary?.currency ?: "",
                            it.schedule?.name ?: "",
                            it.snippet.requirement ?: "",
                            it.snippet.responsibility ?: "",
                            it.experience?.name ?: "",
                            inFavorite = false,
                            keySkill = ""
                        )
                    },
                    found = response.getOrNull()!!.found,
                    page = response.getOrNull()!!.page,
                    pages = response.getOrNull()!!.pages,
                    perPages = VACANCIES_PER_PAGE
                )
                emit(Resource.Success(vacancyList))
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
