package ru.practicum.android.diploma.data.filter

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.repository.FilterRepository

class FilterRepositoryImpl(private val sharedPref: SharedPreferences) : FilterRepository {

    companion object {
        private const val FILTER = "filter"
    }

    override fun getFilter(): Filter {
        val filter = sharedPref.getString(FILTER, null)

        if (filter.isNullOrBlank()) {
            return Filter(null, null, null, null, false)
        }

        val type = object : TypeToken<Filter>() {}.type
        return Gson().fromJson(filter, type)
    }

    override fun saveFilter(filter: Filter): Filter {
        val json = Gson().toJson(filter)
        sharedPref.edit()
            .putString(FILTER, json)
            .apply()

        return filter
    }
}
