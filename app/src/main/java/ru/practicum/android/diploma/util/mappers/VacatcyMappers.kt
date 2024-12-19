@file:Suppress("all")

package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

fun VacancyDetailDto.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id,
        name = this.name,
        areaName = this.area.name,
        employerName = this.employer.name,
        employerLogo = this.employer.logoUrls?.toString() ?: "",
        url = this.alternateUrl,
        salaryFrom = this.salary.from?.toString() ?: "",
        salaryTo = this.salary.to?.toString() ?: "",
        salaryCurrency = this.salary.currency ?: "",
        scheduleName = this.schedule.name,
        snippetRequirement = "",
        snippetResponsibility = "",
        experienceName = this.experience.name ?: "",
        inFavorite = false,
        description = this.description,
        keySkill = this.keySkills.filter { !it.name.isNullOrBlank()  }.joinToString(";") { it.name ?: "" }
    )
}

fun VacancyEntity.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id,
        name = this.name,
        areaName = this.areaName,
        employerName = this.employerName,
        employerLogo = this.employerLogo,
        url = this.url,
        salaryFrom = this.salaryFrom,
        salaryTo = this.salaryTo,
        salaryCurrency = this.salaryCurrency,
        scheduleName = this.scheduleName,
        snippetRequirement = this.snippetRequirement,
        snippetResponsibility = this.snippetResponsibility,
        experienceName = this.experienceName,
        inFavorite = this.inFavorite,
        description = this.description,
        keySkill = this.keySkill
    )
}

fun Vacancy.toVacancyEntity(): VacancyEntity {
    return VacancyEntity(
        id = this.id,
        name = this.name,
        areaName = this.areaName,
        employerName = this.employerName,
        employerLogo = this.employerLogo,
        url = this.url,
        salaryFrom = this.salaryFrom,
        salaryTo = this.salaryTo,
        salaryCurrency = this.salaryCurrency,
        scheduleName = this.scheduleName,
        snippetRequirement = this.snippetRequirement,
        snippetResponsibility = this.snippetResponsibility,
        experienceName = this.experienceName,
        inFavorite = this.inFavorite,
        description = this.description,
        keySkill = this.keySkill
    )
}

fun VacancyDto.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id,
        name = this.name,
        areaName = this.area.name,
        employerName = this.employer.name,
        employerLogo = this.employer.logoUrls?.original ?: "",
        url = this.url,
        salaryFrom = this.salary?.from.toString(),
        salaryTo = this.salary?.to.toString(),
        salaryCurrency = this.salary?.currency ?: "",
        scheduleName = this.schedule?.name ?: "",
        snippetRequirement = this.snippet.requirement ?: "",
        snippetResponsibility = this.snippet.responsibility ?: "",
        experienceName = this.experience?.name ?: "",
        inFavorite = false,
        description = "",
        keySkill = ""
    )
}

