package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RetrofitNetworkClient: NetworkClient {
    override suspend fun <T> executeRequest(request: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = request()
                Result.success(response)
            } catch (e: IOException) {
                Result.failure(e)
            } catch (e: HttpException) {
                Result.failure(e)
            }
        }
    }
}
