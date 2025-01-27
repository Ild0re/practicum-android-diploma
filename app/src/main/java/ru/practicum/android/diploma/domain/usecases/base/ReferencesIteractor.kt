package ru.practicum.android.diploma.domain.usecases.base

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry

interface ReferencesIteractor {
    suspend fun getCountries(): Flow<Pair<List<Area>?, String?>>

    suspend fun getRegions(country: Area): Flow<Pair<List<Area>?, String?>>

    suspend fun getIndustries(): Flow<Pair<List<Industry>?, String?>>

    suspend fun getRegion(country: String): Flow<Pair<Area?, String?>>
}
