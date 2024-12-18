package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.AppDataBase
import ru.practicum.android.diploma.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val hhApi: HhApi,
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter
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

        emit(handleResponse(response))
    }

    private suspend fun handleResponse(response: Result<VacancyResponse?>): Resource<VacancyList> {
        return if (response.isSuccess) {
            response.getOrNull()?.let { apiResponse ->
                val vacancies = apiResponse.items.map { item ->
                    mapToVacancy(item)
                }
                saveDbVacancy(vacancies)
                Resource.Success(
                    VacancyList(
                        vacancies,
                        apiResponse.found,
                        apiResponse.page,
                        apiResponse.pages,
                        VACANCIES_PER_PAGE
                    )
                )
            } ?: Resource.Error("Не удалось получить список вакансий")
        } else {
            val message = response.exceptionOrNull()?.message ?: "Нет интернета"
            when (message.lowercase()) {
                "timeout", "нет интернета" -> Resource.Error("Нет интернета")
                "bad_argument" -> Resource.Error("Не удалось получить список вакансий")
                else -> Resource.Error("Ошибка сервера")
            }
        }
    }

    private fun mapToVacancy(item: VacancyDto): Vacancy {
        return Vacancy(
            id = item.id,
            name = item.name,
            areaName = item.area.name,
            employerName = item.employer.name,
            employerLogo = item.employer.logoUrls?.original ?: "",
            url = item.url,
            salaryFrom = item.salary?.from.toString(),
            salaryTo = item.salary?.to.toString(),
            salaryCurrency = item.salary?.currency ?: "",
            scheduleName = item.schedule?.name ?: "",
            snippetRequirement = item.snippet.requirement ?: "",
            snippetResponsibility = item.snippet.responsibility ?: "",
            experienceName = item.experience?.name ?: "",
            inFavorite = false,
            keySkill = ""
        )
    }
    private suspend fun saveDbVacancy(vacancies: List<Vacancy>) {
        val vacancyEntities = vacancies.map { vacancy -> vacancyDbConverter.map(vacancy) }
        appDataBase.vacancyDao().insertVacancy(vacancyEntities)
    }
}
