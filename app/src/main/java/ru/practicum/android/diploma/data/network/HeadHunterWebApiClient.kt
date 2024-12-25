package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface HeadHunterWebApiClient {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: job_request (gamu@bk.ru)"
    )
    @GET("/vacancies")
    suspend fun getAllVacancies(
        @QueryMap pages: Map<String, Int>,
        @QueryMap options: Map<String, String>
    ): VacancyResponse
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: job_request (gamu@bk.ru)"
    )
    @GET("/vacancies/{id}")
    suspend fun getVacancyById(@Path("id") id: String): VacancyDetailDto

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: job_request (gamu@bk.ru)"
    )
    @GET("/areas/countries")
    suspend fun getCountries(): List<AreaDto>
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: job_request (gamu@bk.ru)"
    )
    @GET("/areas/{id}")
    suspend fun getRegions(@Path("id")id: String): List<AreaDto>
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: job_request (gamu@bk.ru)"
    )
    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>
}
