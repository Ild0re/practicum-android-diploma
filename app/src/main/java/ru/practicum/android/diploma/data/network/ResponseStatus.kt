package ru.practicum.android.diploma.data.network

enum class ResponseStatus {
    EMPTY_RESPONSE, NO_CONNECTION, SERVER_ERROR;

    override fun toString(): String {
        return when (this) {
            EMPTY_RESPONSE -> "Не удалось получить данные"
            NO_CONNECTION -> "Нет интернета"
            SERVER_ERROR -> "Ошибка сервера"
        }
    }
}
