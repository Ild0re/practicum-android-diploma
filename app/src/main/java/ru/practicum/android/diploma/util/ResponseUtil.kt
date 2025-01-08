package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.network.ResponseStatus

fun <T, K> handleResponse(response: Result<T?>, mapper: (T) -> K): Resource<K> {
    return if (response.isSuccess) {
        response.getOrNull()?.let { apiResponse ->
            Resource.Success(mapper(apiResponse))
        } ?: Resource.Error(ResponseStatus.EMPTY_RESPONSE.toString())
    } else {
        val message = response.exceptionOrNull()?.message ?: ResponseStatus.NO_CONNECTION.toString()
        when (message.lowercase()) {
            "timeout", ResponseStatus.NO_CONNECTION.toString() ->
                Resource.Error(ResponseStatus.NO_CONNECTION.toString())
            "bad_argument" -> Resource.Error(ResponseStatus.EMPTY_RESPONSE.toString())
            else -> Resource.Error(ResponseStatus.SERVER_ERROR.toString())
        }
    }
}

fun <T> flowResultHandler(result: Resource<T>): T? {
    when (result) {
        is Resource.Success -> { return result.data }
        is Resource.Error -> { return null }
    }
}
