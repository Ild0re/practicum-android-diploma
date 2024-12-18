package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.util.ApiException
import java.io.IOException

class RetrofitNetworkClient(private val context: Context) : NetworkClient {
    override suspend fun <T> executeRequest(request: suspend () -> T): Result<T> {
        if (!isConnected()) {
            return Result.failure(ApiException.NetworkException("Нет интернета", IOException()))
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = request()
                Result.success(response)
            } catch (e: IOException) {
                Result.failure(ApiException.NetworkException("Нет интернета", e))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> Result.failure(ApiException.BadRequestException(e.message(), e))
                    404 -> Result.failure(ApiException.NotFoundException(e.message(), e))
                    else -> Result.failure(ApiException.GenericApiException(e.message(), e))
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        var connectFlag = false

        if (capabilities != null) {
            connectFlag = connectFlag or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            connectFlag = connectFlag or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            connectFlag = connectFlag or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return connectFlag
    }
}
