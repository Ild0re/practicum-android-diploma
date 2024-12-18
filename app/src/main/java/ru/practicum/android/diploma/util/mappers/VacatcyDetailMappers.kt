@file:Suppress("all")

package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.LanguagesDto
import ru.practicum.android.diploma.data.dto.PhonesDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Languages
import ru.practicum.android.diploma.domain.models.Phones
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyDetail

fun VacancyDetailDto.toVacancyDetail(): VacancyDetail {
    return VacancyDetail(
        acceptHandicapped = this.acceptHandicapped,
        acceptIncompleteResumes = this.acceptIncompleteResumes,
        acceptKids = this.acceptKids,
        acceptTemporary = this.acceptTemporary,
        allowMessages = this.allowMessages,
        alternateUrl = this.alternateUrl,
        applyAlternateUrl = this.applyAlternateUrl,
        approved = this.approved,
        archived = this.archived,
        area = this.area.name,
        brandedDescription = this.brandedDescription,
        canUpgradeBillingType = this.canUpgradeBillingType,
        code = this.code,
        contacts = this.contacts.toContacts(),
        createdAt = this.createdAt,
        department = this.department.name ?: "",
        description = this.description,
        employer = this.employer.toEmployer(),
        employment = this.employment.name ?: "",
        experience = this.experience.name ?: "",
        expiresAt = this.expiresAt,
        hasTest = this.hasTest,
        hidden = this.hidden,
        id = this.id,
        initialCreatedAt = this.initialCreatedAt,
        internship = this.internship,
        keySkills = this.keySkills.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" },
        languages = this.languages.map { it.toLanguages() },
        manager = this.manager.id ?: "",
        name = this.name,
        nightShifts = this.nightShifts,
        premium = this.premium,
        professionalRoles = this.professionalRoles.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" },
        publishedAt = this.publishedAt,
        responseLetterRequired = this.responseLetterRequired,
        responseNotifications = this.responseNotifications,
        responseUrl = this.responseUrl,
        salary = this.salary.toSalary(),
        schedule = this.schedule.name,
        test = this.test.required,
        type = this.type.name,
        workFormat = this.workFormat.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" },
        workScheduleByDays = this.workScheduleByDays.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" },
        workingDays = this.workingDays.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" },
        workingHours = this.workingHours.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" },
        workingTimeIntervals = this.workingTimeIntervals.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" },
        workingTimeModes = this.workingTimeModes.filter { it.name != null && it.name.isNullOrBlank()  }.map { it.name ?: "" }
    )
}

fun EmployerDto.toEmployer(): Employer {
    return Employer(
        name = this.name,
        logoUrls = this.logoUrls?.toString() ?: ""
    )
}

fun ContactsDto.toContacts(): Contacts {
    return Contacts(
        email = this.email,
        name = this.name,
        phones = this.phones.map { it.toPhones() } as ArrayList<Phones>
    )
}

fun LanguagesDto.toLanguages(): Languages {
    return Languages(
        id = this.id,
        level = this.level?.name,
        name = this.name
    )
}

fun SalaryDto.toSalary(): Salary {
    return Salary(
        currency = this.currency,
        from = this.from,
        gross = this.gross,
        to = this.to
    )
}

fun PhonesDto.toPhones(): Phones {
    return Phones(
        city = this.city,
        comment = this.comment,
        country = this.country,
        number = this.number
    )
}
