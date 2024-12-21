package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy
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
    fun bind(item: Vacancy) {
        binding.tvItemVacancyCity.text = "${item.name}, ${item.areaName}"
        binding.tvItemCompany.text = item.employerName
        getPrice(item)
        Glide.with(itemView)
            .load(item.employerLogo)
            .placeholder(R.drawable.logo_placeholder)
            .apply(
                RequestOptions().transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.dim12))
                    )
                )
            )
            .into(binding.imageItem)
    }

    @SuppressLint("SetTextI18n")
    private fun getPrice(item: Vacancy) {
        if (item.salaryFrom == "null" && item.salaryTo == "null") {
            binding.tvItemPrice.text = context.resources.getString(R.string.no_salary)
                .replaceFirstChar { it.lowercase() }
        } else if (item.salaryFrom != "null" && item.salaryTo == "null") {
            binding.tvItemPrice.text =
                "От ${formatNumberWithSpaces(item.salaryFrom)} ${getCurrencySymbol(item.salaryCurrency)}"
                    .replaceFirstChar { it.lowercase() }
        } else if (item.salaryFrom == "null" && item.salaryTo != "null") {
            binding.tvItemPrice.text =
                "До ${formatNumberWithSpaces(item.salaryTo)} ${getCurrencySymbol(item.salaryCurrency)}"
                    .replaceFirstChar { it.lowercase() }
        } else {
            binding.tvItemPrice.text =
                "От ${formatNumberWithSpaces(item.salaryFrom)} до ${formatNumberWithSpaces(item.salaryTo)} ${
                    getCurrencySymbol(item.salaryCurrency)
                }".replaceFirstChar { it.lowercase() }
        }
    }

    private fun formatNumberWithSpaces(number: String): String {
        val df = DecimalFormat()
        df.isGroupingUsed = true
        df.groupingSize = INT_GROUP_SIZE

        val symbols = DecimalFormatSymbols(Locale.getDefault())
        symbols.groupingSeparator = ' '
        df.decimalFormatSymbols = symbols

        return df.format(number.toInt())
    }
}
