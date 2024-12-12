package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.util.isConnected

class RetrofitNetworkClient(
    private val hhApi: HhApi,
    private val context: Context
) : NetworkClient {
    companion object{
        const val SUCCESS_REQUEST = 200
        const val BAD_REQUEST = 400
        const val INTERNAL_SERVER_ERROR = 500
        const val NO_CONNECT = -1
    }
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected(context)) {
            return Response().apply { resultCode = NO_CONNECT }
        }
        if (dto !is VacancySearchRequest) {
            return Response().apply { resultCode = BAD_REQUEST }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = hhApi.search(dto.expression)
                response.apply { resultCode = SUCCESS_REQUEST }
            } catch (e: Throwable) {
                Response().apply { resultCode = INTERNAL_SERVER_ERROR }
            }
        }
    }
}
