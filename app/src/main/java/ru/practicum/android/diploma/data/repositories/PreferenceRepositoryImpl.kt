package ru.practicum.android.diploma.data.repositories

import android.content.Context
import ru.practicum.android.diploma.domain.repository.PreferenceRepository

class PreferenceRepositoryImpl(context: Context) : PreferenceRepository {
    companion object {
        private const val PREFERENCES_NAME = "diploma_app_preferences"
        private const val SLC_KEY = "SLC"
    }

    private val sharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override fun saveSalaryClose(value: Boolean) {
        sharedPreferences.edit().putBoolean(SLC_KEY, value).apply()
    }

    override fun getSalaryClose(): Boolean {
        return sharedPreferences.getBoolean(SLC_KEY, false)
    }
}
