package ru.practicum.android.diploma.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

object ClickDebounce {

    private const val CLICK_DEBOUNCE_DELAY = 1000L //задержка после нажатия

    private val clickDebounceScope = CoroutineScope(Dispatchers.Default)

    fun clickDebounce(button: View, action: () -> Unit) {
        button.isEnabled = false
        action()
        button.postDelayed({ button.isEnabled = true }, CLICK_DEBOUNCE_DELAY)
    }

    fun cancel(){
        clickDebounceScope.cancel()
    }
}
