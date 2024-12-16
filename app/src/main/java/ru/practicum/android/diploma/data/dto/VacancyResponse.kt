package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    @SerializedName("per_pages")
    val perPages: Int
) : Response()
