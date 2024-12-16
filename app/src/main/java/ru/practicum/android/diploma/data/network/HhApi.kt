package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface HhApi {
    @Headers(
        "Authorization: Bearer APPLUN1VS0I1RP6RKU9EEJ7A9KUBSR1TUOIV5SLPN8DIHNC72184FPTQ5NPH1CPA",
        "HH-User-Agent: job_request (gamu@bk.ru)"
    )
    @GET("/vacancies")
    suspend fun getAllVacancies(
        @QueryMap pages: Map<String, Int>,
        @QueryMap options: Map<String, String>
    ): VacancyResponse
}
