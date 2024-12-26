package ru.practicum.android.diploma.domain.usecases.base

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry

interface ReferencesIteractor {
    suspend fun getCountries(): Flow<List<Area>?>

    suspend fun getRegions(country: Area): Flow<List<Area>?>

    suspend fun getIndustries(): Flow<Pair<List<Industry>?, String?>>
}
