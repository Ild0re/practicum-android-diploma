package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.AppDataBase
import ru.practicum.android.diploma.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.HeadHunterWebApiClient
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.mappers.toVacancyDetail

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val webApiClient: HeadHunterWebApiClient,
    private val appDataBase: AppDataBase,
    private val vacancyDbConverter: VacancyDbConverter
) : VacancyRepository {

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
            webApiClient.getAllVacancies(pages, options)
        }

        emit(handleResponse(response, ::toVacancyResponse))
    }

    override suspend fun getVacancyById(id: String): Flow<Resource<VacancyDetail>> = flow {
        val response = networkClient.executeRequest {
            webApiClient.getVacancyById(id)
        }

        emit(handleResponse(response, ::toVacancyDetail))
    }

    override suspend fun saveDbVacancy(vacancies: List<Vacancy>) {
        val vacancyEntities = vacancies.map { vacancy -> vacancyDbConverter.map(vacancy) }
        appDataBase.vacancyDao().insertVacancy(vacancyEntities)
    }

    private fun <T, K> handleResponse(response: Result<T?>, mapper: (T) -> K): Resource<K> {
        return if (response.isSuccess) {
            response.getOrNull()?.let { apiResponse ->
                Resource.Success(mapper(apiResponse))
            } ?: Resource.Error("Не удалось получить данные")
        } else {
            val message = response.exceptionOrNull()?.message ?: "Нет интернета"
            when (message.lowercase()) {
                "timeout", "нет интернета" -> Resource.Error("Нет интернета")
                "bad_argument" -> Resource.Error("Не удалось получить данные")
                else -> Resource.Error("Ошибка сервера")
            }
        }
    }

    private fun toVacancyDetail(vacancyDetailDto: VacancyDetailDto): VacancyDetail {
        return vacancyDetailDto.toVacancyDetail()
    }


    private fun toVacancyResponse(vacancyResponse: VacancyResponse): VacancyList {
        val vacancies = vacancyResponse.items.map { item ->
            mapToVacancy(item)
        }
        return VacancyList(
            vacancies,
            vacancyResponse.found,
            vacancyResponse.page,
            vacancyResponse.pages,
            VACANCIES_PER_PAGE
        )
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

}
