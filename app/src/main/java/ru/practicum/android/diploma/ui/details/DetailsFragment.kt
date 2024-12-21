package ru.practicum.android.diploma.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailedInformationBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.viewmodel.VacancyDetailViewModel
import ru.practicum.android.diploma.util.VacancyState
import ru.practicum.android.diploma.util.getCurrencySymbol
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class DetailsFragment : Fragment() {

    companion object {
        private const val VACANCY_ID = "id"
        private const val RESPONSIBILITY = "responsibility"
        private const val REQUIREMENT = "requirements"
        fun newInstance(id: String, responsibility: String, requirement: String) = DetailsFragment().apply {
            arguments = bundleOf(VACANCY_ID to id, RESPONSIBILITY to responsibility, REQUIREMENT to requirement)
        }

        const val VACANCIES_LOAD_ERROR = "Не удалось получить список вакансий"
        private const val INT_GROUP_SIZE = 3
    }

    private var _binding: FragmentDetailedInformationBinding? = null
    val binding: FragmentDetailedInformationBinding
        get() = _binding!!

    private val viewModel by viewModel<VacancyDetailViewModel> {
        parametersOf(requireArguments().getString(VACANCY_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupEventHandler()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = true
    }

    private fun setupObservers() {
        viewModel.observeVacancyState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyState.Loading -> showLoading()
                is VacancyState.Error -> showError(state.message)
                is VacancyState.Content -> {
                    showData(state.data)
                }
            }
        }
        viewModel.observeFavourites().observe(viewLifecycleOwner) { boolean ->
            renderBoolean(boolean)
        }
    }

    private fun setupEventHandler() {
        binding.backArrow.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.lickedIcon.setOnClickListener {
            viewModel.onFavouriteClicked()
        }
        binding.shareButton.setOnClickListener {
            startActivity(viewModel.shareVacancy())
        }
    }

    private fun showLoading() {
        binding.detailInformation.isVisible = false
        binding.placeholder.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun showError(error: String) {
        binding.detailInformation.isVisible = false
        binding.placeholder.isVisible = true
        binding.progressBar.isVisible = false
        if (error == VACANCIES_LOAD_ERROR) {
            binding.vacancyPlaceholder.setImageResource(R.drawable.empty_vacancy)
            binding.vacancyNotFound.text = getString(R.string.vacancy_not_found)
        } else {
            binding.vacancyPlaceholder.setImageResource(R.drawable.details_server_error)
            binding.vacancyNotFound.text = getString(R.string.tv_main_error_server)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showData(data: Vacancy) {
        binding.detailInformation.isVisible = true
        binding.placeholder.isVisible = false
        binding.progressBar.isVisible = false
        if (data.keySkill != "") {
            binding.keySkills.isVisible = true
            binding.keyList.text = data.keySkill
        } else {
            binding.keySkills.isVisible = false
        }
        checkResponsibilitiesAndRequirements()
        binding.conditionsText.text = Html.fromHtml(data.description, Html.FROM_HTML_MODE_COMPACT)
        binding.tvExperience.text = data.experienceName
        binding.typeOfEmployment.text = "${data.employmentForm}, ${data.scheduleName}"
        binding.address.text = data.areaName
        binding.employer.text = data.employerName
        binding.vacancyName.text = data.name
        getPrice(data)
        Glide.with(requireContext())
            .load(data.employerLogo)
            .placeholder(R.drawable.logo_placeholder)
            .apply(
                RequestOptions().transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.dim12))
                    )
                )
            )
            .into(binding.logoCompany)
    }

    @SuppressLint("SetTextI18n")
    private fun getPrice(item: Vacancy) {
        if (item.salaryFrom == "null" && item.salaryTo == "null") {
            binding.salaryInformation.text = getString(R.string.no_salary)
        } else if (item.salaryFrom != "null" && item.salaryTo == "null") {
            binding.salaryInformation.text =
                "От ${formatNumberWithSpaces(item.salaryFrom)} ${getCurrencySymbol(item.salaryCurrency)}"
        } else if (item.salaryFrom == "null" && item.salaryTo != "null") {
            binding.salaryInformation.text =
                "До ${formatNumberWithSpaces(item.salaryTo)} ${getCurrencySymbol(item.salaryCurrency)}"
        } else {
            binding.salaryInformation.text =
                "От ${formatNumberWithSpaces(item.salaryFrom)} до ${formatNumberWithSpaces(item.salaryTo)} ${
                    getCurrencySymbol(item.salaryCurrency)
                }"
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

    private fun checkResponsibilitiesAndRequirements() {
        if (requireArguments().getString(REQUIREMENT) != "") {
            binding.requirements.isVisible = true
            binding.requirementsText.text = requireArguments().getString(REQUIREMENT)
        } else {
            binding.requirements.isVisible = false
        }
        if (requireArguments().getString(RESPONSIBILITY) != "") {
            binding.responsibilities.isVisible = true
            binding.responsibilitiesText.text = requireArguments().getString(RESPONSIBILITY)
        } else {
            binding.responsibilities.isVisible = false
        }
    }

    private fun renderBoolean(boolean: Boolean) {
        when (boolean) {
            true -> binding.lickedIcon.setImageResource(R.drawable.ic_liked)
            else -> binding.lickedIcon.setImageResource(R.drawable.ic_unliked)
        }
    }
}
