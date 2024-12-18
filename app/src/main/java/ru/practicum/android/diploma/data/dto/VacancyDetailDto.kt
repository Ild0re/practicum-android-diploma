@file:Suppress("all")
package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetailDto(
    @SerializedName("accept_handicapped")
    val acceptHandicapped: Boolean,
    @SerializedName("accept_incomplete_resumes")
    val acceptIncompleteResumes: Boolean,
    @SerializedName("accept_kids")
    val acceptKids: Boolean,
    @SerializedName("accept_temporary")
    val acceptTemporary: Boolean,
    @SerializedName("allow_messages")
    val allowMessages: Boolean,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("apply_alternate_url")
    val applyAlternateUrl: String,
    val approved: Boolean,
    val archived: Boolean,
    val area: AreaDto,
    @SerializedName("billing_type")
    val billingType: BillingTypeDto,
    @SerializedName("branded_description")
    val brandedDescription: String,
    @SerializedName("branded_template")
    val brandedTemplate: BrandedTemplateDto,
    @SerializedName("can_upgrade_billing_type")
    val canUpgradeBillingType: Boolean,
    val code: String,
    val contacts: ContactsDto,
    @SerializedName("created_at")
    val createdAt: String,
    val department: DepartmentDto,
    val description: String,
    val employer: EmployerDto,
    val employment: EmploymentDto,
    val experience: ExperienceDto,
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("has_test")
    val hasTest: Boolean,
    val hidden: Boolean,
    val id: String,
    @SerializedName("initial_created_at")
    val initialCreatedAt: String,
    val internship: Boolean,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillsDto>,
    val languages: List<LanguagesDto>,
    val manager: ManagerDto,
    val name: String,
    @SerializedName("night_shifts")
    val nightShifts: Boolean,
    val premium: Boolean,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRolesDto>,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("response_letter_required")
    val responseLetterRequired: Boolean,
    @SerializedName("response_notifications")
    val responseNotifications: Boolean,
    @SerializedName("response_url")
    val responseUrl: Any?,
    val salary: SalaryDto,
    val schedule: ScheduleDto,
    val test: TestDto,
    val type: TypeDto,
    @SerializedName("work_format")
    val workFormat: List<WorkFormatDto>,
    @SerializedName("work_schedule_by_days")
    val workScheduleByDays: List<WorkScheduleByDaysDto>,
    @SerializedName("working_days")
    val workingDays: List<WorkingDaysDto>,
    @SerializedName("working_hours")
    val workingHours: List<WorkingHoursDto>,
    @SerializedName("working_time_intervals")
    val workingTimeIntervals: List<WorkingTimeIntervalsDto>,
    @SerializedName("working_time_modes")
    val workingTimeModes: List<WorkingTimeModesDto>)
