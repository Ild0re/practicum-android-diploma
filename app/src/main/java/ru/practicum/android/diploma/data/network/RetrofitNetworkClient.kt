package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.util.isConnected

class RetrofitNetworkClient(
    private val hhApi: HhApi,
    private val context: Context) : NetworkClient
{
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected(context)) {
            return Response().apply { resultCode - 1 }
        }
        if (dto !is VacancySearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO){
            try {
                val response = hhApi.search(dto.expression)
                response.apply { resultCode = 200 }
            } catch (e:Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

}
