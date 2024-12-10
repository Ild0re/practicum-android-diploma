package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

object SearchDebounceUtil {

    private const val SEARCH_DEBOUNCE_DELAY = 2000L // задержка поиска

    private val debounceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun <T> searchDebounce(
        delayMillis: Long,
        input: Flow<T>,
        loadData: suspend (T) -> Unit
    ) {
        debounceScope.launch {
            input.debounce(delayMillis).collect {
                loadData(it)
            }
        }
    }

    fun cancel() {
        debounceScope.cancel()
    }
}
