package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.LogoDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Logo
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyItem


fun VacancyResponse.toVacancy(perPage: Int): Vacancy {
    return Vacancy(
        items = items.map { it.toVacancyItem() },
        found = found,
        page = page,
        pages = pages,
        perPages = perPage
    )
}

fun VacancyDto.toVacancyItem(): VacancyItem {
    return VacancyItem(
        id = id,
        name = name,
        area = area.toArea(),
        salary = salary!!.toSalary(),
        employer = employer.toEmployer(),
        url = url,
    )
}

fun AreaDto.toArea(): Area {
    return Area(id = id, name = name, url = url)
}

fun EmployerDto.toEmployer(): Employer {
    return Employer(name = name, logoUrls = logoUrls?.toLogo())
}

fun LogoDto.toLogo(): Logo {
    return Logo(original = original)
}

fun SalaryDto.toSalary(): Salary {
    return Salary(currency = currency, from = from, gross = gross, to = to)
}
