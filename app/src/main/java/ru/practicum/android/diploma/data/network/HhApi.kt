package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface HhApi {

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: job_request (gamu@bk.ru)"
    )
    @GET("/vacancies")
    suspend fun getAllVacancies(
        @QueryMap pages: Map<String, Int>,
        @QueryMap options: Map<String, String>
    ): VacancyResponse
}
