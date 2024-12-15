package ru.practicum.android.diploma.data.network

interface NetworkClient {
    suspend fun <T> executeRequest(request: suspend () -> T): Result<T>
}
