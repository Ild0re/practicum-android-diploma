package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.AppDataBase
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.HeadHunterWebApiClient
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyList
import ru.practicum.android.diploma.domain.repository.FilterRepository
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.mappers.toVacancy

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val webApiClient: HeadHunterWebApiClient,
    private val appDataBase: AppDataBase,
    private val filter: FilterRepository
) : VacancyRepository {

    companion object {
        private const val VACANCIES_PER_PAGE = 20
    }

    override suspend fun getAllVacancies(expression: String, page: Int): Flow<Resource<VacancyList>> = flow {
        val pages: HashMap<String, Int> = HashMap()
        val options: HashMap<String, String> = HashMap()

        val filter = filter.getFilter()

        options["text"] = expression
        options["search_field"] = "name"
        if (filter.country != null) {
            if (filter.area != null) {
                options["area"] = filter.area.id
            } else {
                options["area"] = filter.country.id
            }
        }
        if (filter.scope != null) {
            options["industry"] = filter.scope.id
        }
        if (filter.salary != null) {
            options["salary"] = filter.salary
        }
        if (filter.isOnlyWithSalary) {
            options["only_with_salary"] = "true"
        } else {
            options["only_with_salary"] = "false"
        }
        pages["page"] = page
        pages["per_page"] = VACANCIES_PER_PAGE

        val response = networkClient.executeRequest {
            webApiClient.getAllVacancies(pages, options)
        }

        emit(handleResponse(response, ::toVacancyResponse))
    }

    override suspend fun getVacancyById(id: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.executeRequest {
            webApiClient.getVacancyById(id)
        }
        emit(handleResponse(response, ::toVacancyDetail))
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

    private fun toVacancyDetail(vacancyDetailDto: VacancyDetailDto): Vacancy {
        return vacancyDetailDto.toVacancy()
    }

    private fun toVacancyResponse(vacancyResponse: VacancyResponse): VacancyList {
        val vacancies = vacancyResponse.items.map { item ->
            item.toVacancy()
        }
        return VacancyList(
            vacancies,
            vacancyResponse.found,
            vacancyResponse.page,
            vacancyResponse.pages,
            VACANCIES_PER_PAGE
        )
    }

    override suspend fun getFavouritesFromIds(data: Vacancy): Vacancy {
        val idList = appDataBase.vacancyDao().getVacancyIds()
        return if (data.id in idList) {
            data.copy(inFavorite = true)
        } else {
            data
        }
    }
}
