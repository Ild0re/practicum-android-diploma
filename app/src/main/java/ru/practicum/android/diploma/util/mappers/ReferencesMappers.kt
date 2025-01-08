package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry

fun AreaDto.toArea(): Area {
    return Area(
        id = this.id ?: "0",
        name = this.name ?: "",
        url = this.url ?: "",
        parentId = this.parentId ?: "",
        areas = this.areas?.map { i -> i.toArea() } ?: emptyList()
    )
}

fun IndustryDto.toIndustry(): Industry {
    return Industry(
        id = this.id.toString(),
        name = this.name,
        industries = this.industries?.map { i -> i.toIndustry() }
    )
}
