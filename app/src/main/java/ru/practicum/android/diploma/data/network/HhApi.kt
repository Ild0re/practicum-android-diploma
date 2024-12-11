package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface HhApi {
    @GET("/vacancies?text=")
    suspend fun search(@Query("term")text:String) : VacancyResponse
}
