package ru.practicum.android.diploma.util

sealed class ApiException : Exception() {
    class BadRequestException(message: String? = null, cause: Throwable? = null) : ApiException()
    class NotFoundException(message: String? = null, cause: Throwable? = null) : ApiException()
    class GenericApiException(message: String? = null, cause: Throwable? = null) : ApiException()

    class NetworkException(message: String, cause: Throwable? = null) : ApiException()
}
