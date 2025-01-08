package ru.practicum.android.diploma.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.usecases.base.FilterInteractor
import ru.practicum.android.diploma.domain.usecases.base.ReferencesIteractor
import ru.practicum.android.diploma.util.FilterField

class FilterSettingViewModel(
    private val filterInteractor: FilterInteractor,
    private val referencesInteractor: ReferencesIteractor
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 100L
    }

    init {
        loadFilter()
    }

    private var filter: Filter = Filter(null, null, null, null, false)
    private var countryObject: Area? = null
    private var areaObject: Area? = null
    private var industryObject: Industry? = null

    private val filterState = MutableLiveData<Filter>()

    fun getFilter(): LiveData<Filter> = filterState

    fun loadFilter() {
        viewModelScope.launch {
            val filterObject = filterInteractor.getFilter()
            filter = filterInteractor.getFilter()
            delay(SEARCH_DEBOUNCE_DELAY)
            postFilter(filterObject)
        }
    }

    private fun postFilter(filter: Filter) {
        filterState.postValue(filter)
    }

    fun saveFilter(country: String?, area: String?, scope: String?, salary: String?, noSalary: Boolean): Filter {
        countryObject = if (country == null) {
            null
        } else {
            filter.country
        }
        areaObject = if (area == null) {
            null
        } else {
            filter.area
        }
        industryObject = if (scope == null) {
            null
        } else {
            filter.scope
        }
        return filterInteractor.saveFilter(Filter(countryObject, areaObject, industryObject, salary, noSalary))
    }

    fun updateFilter(bottom: Boolean) {
        filterInteractor.updateFilter(FilterField.ONLY_WITH_SALARY, bottom)
    }
}
