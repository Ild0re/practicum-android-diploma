package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.db.entities.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbConverter {
    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.areaName,
            vacancy.employerName,
            vacancy.employerLogo,
            vacancy.url,
            vacancy.salaryFrom,
            vacancy.salaryTo,
            vacancy.salaryCurrency,
            vacancy.scheduleName,
            vacancy.snippetRequirement,
            vacancy.snippetResponsibility,
            vacancy.experienceName,
            vacancy.inFavorite,
            vacancy.keySkill
        )
    }

    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            vacancy.id,
            vacancy.name,
            vacancy.areaName,
            vacancy.employerName,
            vacancy.employerLogo,
            vacancy.url,
            vacancy.salaryFrom,
            vacancy.salaryTo,
            vacancy.salaryCurrency,
            vacancy.scheduleName,
            vacancy.snippetRequirement,
            vacancy.snippetResponsibility,
            vacancy.experienceName,
            vacancy.inFavorite,
            vacancy.keySkill
        )
    }
}
