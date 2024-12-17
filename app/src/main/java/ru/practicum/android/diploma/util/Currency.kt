package ru.practicum.android.diploma.util

enum class Currency(val symbol: String) {
    RUR("₽"),
    RUB("₽"),
    BYR("Br"),
    USD("$"),
    EUR("€"),
    KZT("₸"),
    UAH("₴"),
    AZN("₼"),
    UZS("сум"),
    GEL("₾"),
    KGT("сом")
}

fun getCurrencySymbol(currencyCode: String): String? {
    return Currency.entries.find { it.name == currencyCode }?.symbol
}
