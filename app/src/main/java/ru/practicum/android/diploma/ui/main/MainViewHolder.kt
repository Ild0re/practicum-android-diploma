package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacancyItem
import ru.practicum.android.diploma.util.getCurrencySymbol
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class MainViewHolder(private val binding: VacancyItemBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val INT_GROUP_SIZE = 3
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: VacancyItem) {
        // заменить VacancyDto на Vacancy
        binding.tvItemVacancyCity.text = "${item.name}, ${item.area.name}"
        binding.tvItemCompany.text = item.employer.name
        getPrice(item)
        Glide.with(itemView)
            .load(item.employer.logoUrls?.original)
            .placeholder(R.drawable.logo_placeholder)
            .centerCrop()
            .transform(
                RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.dim12))
            )
            .into(binding.imageItem)
    }

    @SuppressLint("SetTextI18n")
    private fun getPrice(item: VacancyItem) {
        // заменить VacancyDto на Vacancy
        if (item.salary?.from == null && item.salary?.to == null) {
            binding.tvItemPrice.text = context.resources.getString(R.string.no_salary)
        } else if (item.salary.from != null && item.salary.to == null) {
            binding.tvItemPrice.text =
                "От ${formatNumberWithSpaces(item.salary.from)} ${getCurrencySymbol(item.salary.currency.toString())}"
        } else if (item.salary.from == null && item.salary.to != null) {
            binding.tvItemPrice.text =
                "До ${formatNumberWithSpaces(item.salary.to)} ${getCurrencySymbol(item.salary.currency.toString())}"
        } else {
            binding.tvItemPrice.text =
                "От ${formatNumberWithSpaces(item.salary.from!!)} до ${formatNumberWithSpaces(item.salary.to!!)} ${
                    getCurrencySymbol(
                        item.salary.currency.toString()
                    )
                }"
        }
    }

    private fun formatNumberWithSpaces(number: Int): String {
        val df = DecimalFormat()
        df.isGroupingUsed = true
        df.groupingSize = INT_GROUP_SIZE

        val symbols = DecimalFormatSymbols(Locale.getDefault())
        symbols.groupingSeparator = ' '
        df.decimalFormatSymbols = symbols

        return df.format(number)
    }
}
