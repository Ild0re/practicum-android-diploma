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

    private var countryList: List<Area>? = null
    private var areaList: List<Area>? = null
    private var industryList: List<Industry>? = null

    private val filterState = MutableLiveData<Filter>()

    fun getFilter(): LiveData<Filter> = filterState

    private fun loadFilter() {
        getCountryIds()
        getIndustriesIds()
        viewModelScope.launch {
            val filter = filterInteractor.getFilter()
            delay(SEARCH_DEBOUNCE_DELAY)
            postFilter(filter)
        }
    }

    private fun postFilter(filter: Filter) {
        filterState.postValue(filter)
    }

    fun saveFilter(country: String?, area: String?, scope: String?, salary: String?, noSalary: Boolean): Filter {
        val countryObject = countryList?.find { it.name == country }
        if (countryObject != null) {
            getRegionId(countryObject)
        }
        val industryObject = industryList?.find { it.name == scope }
        val areaObject = areaList?.find { it.name == area }
        return filterInteractor.saveFilter(Filter(countryObject, areaObject, industryObject, salary, noSalary))
    }

    private fun getCountryIds() {
        viewModelScope.launch {
            referencesInteractor.getCountries().collect { data -> countryList = data }
        }
    }

    private fun getIndustriesIds() {
        viewModelScope.launch {
            referencesInteractor.getIndustries().collect { data -> industryList = data }
        }
    }

    private fun getRegionId(country: Area) {
        viewModelScope.launch {
            referencesInteractor.getRegions(country).collect { data -> areaList = data }
        }
    }
}
