package ru.practicum.android.diploma.domain.models

class VacancyItem(
    val id: String,
    val name: String,
    val area: Area,
    val employer: Employer,
    val salary: Salary,
    val url: String
)

class Area(val id: String, val name: String, val url: String)

class Logo(val original: String)

class Employer(val name: String, val logoUrls: Logo?)

class Salary(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)
