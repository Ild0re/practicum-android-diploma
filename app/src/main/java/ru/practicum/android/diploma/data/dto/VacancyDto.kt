package ru.practicum.android.diploma.data.dto

class VacancyDto(
    val id: String,
    val name: String,
    val area: AreaDto,
    val employer: EmployerDto,
    val salary: SalaryDto?,
    val url: String
)
