package ru.practicum.android.diploma.data.filter

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.repository.FilterRepository
import ru.practicum.android.diploma.util.FilterField

class FilterRepositoryImpl(private val sharedPref: SharedPreferences) : FilterRepository {

    companion object {
        private const val FILTER = "filter"
    }

    override fun getFilter(): Filter {
        val filter = sharedPref.getString(FILTER, null)

        if (filter.isNullOrBlank()) {
            return Filter(
                null,
                null,
                null,
                null,
                false
            )
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

    override fun updateFilter(field: FilterField, value: Any?): Filter {
        val currentFilter = getFilter()

        val updatedFilter = when (field) {
            FilterField.COUNTRY -> currentFilter.copy(country = value as? Area)
            FilterField.REGION -> currentFilter.copy(area = value as? Area)
            FilterField.INDUSTRY -> currentFilter.copy(scope = value as? Industry)
            FilterField.SALARY -> currentFilter.copy(salary = value as? String)
            FilterField.ONLY_WITH_SALARY -> currentFilter.copy(isOnlyWithSalary = value as? Boolean ?: false)
        }

        saveFilter(updatedFilter)
        return updatedFilter
    }
}
