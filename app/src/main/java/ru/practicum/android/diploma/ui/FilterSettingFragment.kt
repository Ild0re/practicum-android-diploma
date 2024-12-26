package ru.practicum.android.diploma.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingFilterBinding
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.ui.viewmodel.FilterSettingViewModel
import ru.practicum.android.diploma.ui.viewmodel.MainViewModel

class FilterSettingFragment : Fragment() {
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var isClickAllowed = true
    private var _binding: FragmentSettingFilterBinding? = null
    val binding: FragmentSettingFilterBinding
        get() = _binding!!

    var isDrawableChanged = false

    private var country: String? = null
    private var area: String? = null
    private var scope: String? = null
    private var salary: String? = null

    private val viewModel by viewModel<FilterSettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupObservers()
        setupEventHandlers()
    }

    private fun setupTextWatcher() {
        val salaryTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (!p0.isNullOrEmpty()) {
                    binding.imageClear.isVisible = true
                } else {
                    binding.imageClear.isVisible = false
                }
                visibleBottom(
                    binding.etWorkingPlaceHint.text.toString(),
                    binding.etIndustryHint.text.toString(),
                    binding.etSalaryHint.text.toString(),
                    isDrawableChanged
                )
            }

            override fun afterTextChanged(p0: Editable?) {
                // в данный момент не используется
            }
        }
        binding.etSalaryHint.addTextChangedListener(salaryTextWatcher)
        val industryTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (!p0.isNullOrEmpty()) {
                    binding.etIndustry.setEndIconDrawable(R.drawable.main_clear_icon)
                } else {
                    binding.etIndustry.setEndIconDrawable(R.drawable.filter_arrow_right_icon)
                }
                visibleBottom(
                    binding.etWorkingPlaceHint.text.toString(),
                    binding.etIndustryHint.text.toString(),
                    binding.etSalaryHint.text.toString(),
                    isDrawableChanged
                )
            }

            override fun afterTextChanged(p0: Editable?) {
                // в данный момент не используется
            }
        }
        binding.etIndustryHint.addTextChangedListener(industryTextWatcher)
        val workingPlaceTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (!p0.isNullOrEmpty()) {
                    binding.etWorkingPlace.setEndIconDrawable(R.drawable.main_clear_icon)
                } else {
                    binding.etWorkingPlace.setEndIconDrawable(R.drawable.filter_arrow_right_icon)
                }
                visibleBottom(
                    binding.etWorkingPlaceHint.text.toString(),
                    binding.etIndustryHint.text.toString(),
                    binding.etSalaryHint.text.toString(),
                    isDrawableChanged
                )
            }

            override fun afterTextChanged(p0: Editable?) {
                // в данный момент не используется
            }
        }
        binding.etWorkingPlaceHint.addTextChangedListener(workingPlaceTextWatcher)
    }

    private fun setupEventHandlers() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etIndustryHint.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                findNavController().navigate(R.id.action_filterSettingFragment_to_choosingIndustryFragment)
            }
        }
        binding.etWorkingPlaceHint.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                findNavController().navigate(R.id.action_filterSettingFragment_to_choosingWorkingPlaceFragment)
            }
        }
        clearSalary()
        clearWorkingPlace()
        clearIndustry()
        clickSalaryClose()
        resetBottom()
        binding.etSalaryHint.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // пока не используется
            }
            false
        }
        binding.etWorkingPlaceHint.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // пока не используется
            }
            false
        }
        binding.etIndustryHint.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // пока не используется
            }
            false
        }
        binding.btApply.setOnClickListener {
            clickApply()
        }
    }

    private fun setupObservers() {
        viewModel.getFilter().observe(viewLifecycleOwner) { filter ->
            loadFilter(filter)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = true
    }

    private fun clearSalary() {
        binding.imageClear.setOnClickListener {
            binding.etSalaryHint.setText("")
            if (binding.etSalaryHint.text.isNullOrEmpty()) {
                binding.imageClear.isVisible = false
            }
        }
    }

    private fun clearWorkingPlace() {
        binding.etWorkingPlace.setEndIconOnClickListener {
            binding.etWorkingPlaceHint.setText("")
            if (binding.etWorkingPlaceHint.text.isNullOrEmpty()) {
                binding.etWorkingPlace.setEndIconDrawable(R.drawable.filter_arrow_right_icon)
            }
        }
    }

    private fun clearIndustry() {
        binding.etIndustry.setEndIconOnClickListener {
            binding.etIndustryHint.setText("")
            if (binding.etIndustryHint.text.isNullOrEmpty()) {
                binding.etIndustry.setEndIconDrawable(R.drawable.filter_arrow_right_icon)
            }
        }
    }

    private fun clickSalaryClose() {
        binding.salaryClose.setOnClickListener {
            visibleBottom(
                binding.etWorkingPlaceHint.text.toString(),
                binding.etIndustryHint.text.toString(),
                binding.etSalaryHint.text.toString(),
                isDrawableChanged
            )
            changeDrawableSalary()
            isDrawableChanged = !isDrawableChanged
        }
    }

    private fun visibleBottom(workplace: String, industry: String, salary: String, click: Boolean) {
        if (listOf(workplace, industry, salary).any { it.isNotBlank() } || click) {
            binding.reset.isVisible = true
            binding.btApply.isVisible = true
        } else {
            binding.reset.isVisible = false
            binding.btApply.isVisible = false
        }
    }

    private fun resetBottom() {
        binding.reset.setOnClickListener {
            binding.etWorkingPlaceHint.setText("")
            binding.etIndustryHint.setText("")
            binding.etSalaryHint.setText("")
            binding.salaryClose.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.filter_square_disable_icon,
                0
            )
            isDrawableChanged = !isDrawableChanged
            binding.reset.isVisible = false
            binding.btApply.isVisible = false
        }
    }

    private fun clickApply() {
        if (clickDebounce()) {
                if (binding.etWorkingPlaceHint.text.toString() != "") {
                    val listOfAreas = binding.etWorkingPlaceHint.text.toString().split(",")
                    if (listOfAreas.size > 1) {
                        country = listOfAreas[0]
                        area = listOfAreas[1]
                    } else {
                        country = listOfAreas[0]
                    }
                } else {
                    country = null
                }
            scope = if (binding.etIndustryHint.text.toString() != "") {
                binding.etIndustryHint.text.toString()
            } else {
                null
            }
            salary = if (binding.etSalaryHint.text.toString() != "") {
                binding.etSalaryHint.text.toString()
            } else {
                null
            }
            val withSalary = isDrawableChanged
            viewModel.saveFilter(country, area, scope, salary, withSalary)
            val bundle = Bundle()
            bundle.putBoolean("fromFragmentFilter", true)
            findNavController().navigate(R.id.action_filterSettingFragment_to_mainFragment)
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        viewLifecycleOwner.lifecycleScope.launch {
            delay(CLICK_DEBOUNCE_DELAY)
            isClickAllowed = true
        }
        return current
    }

    @SuppressLint("SetTextI18n")
    private fun loadFilter(data: Filter) {
        if (data.country != null) {
            if (data.area != null) {
                binding.etWorkingPlaceHint.setText("${data.country.name}, ${data.area.name}")
            } else {
                binding.etWorkingPlaceHint.setText(data.country.name)
            }
        } else {
            binding.etWorkingPlaceHint.setText("")
        }
        if (data.scope != null) {
            binding.etIndustryHint.setText(data.scope.name)
        } else {
            binding.etIndustryHint.setText("")
        }
        if (data.salary != null) {
            binding.etSalaryHint.setText(data.salary)
        } else {
            binding.etSalaryHint.setText("")
        }
        if (data.isOnlyWithSalary) {
            isDrawableChanged = true
            changeDrawableSalary()
        } else {
            isDrawableChanged = false
            changeDrawableSalary()
        }
    }

    private fun changeDrawableSalary() {
        if (isDrawableChanged) {
            binding.salaryClose.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.filter_square_activated_icon,
                0
            )
        } else {
            binding.salaryClose.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.filter_square_disable_icon,
                0
            )
        }
    }
}
