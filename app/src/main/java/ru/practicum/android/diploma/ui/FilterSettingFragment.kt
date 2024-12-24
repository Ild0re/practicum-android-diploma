package ru.practicum.android.diploma.ui

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
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingFilterBinding

class FilterSettingFragment : Fragment() {
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var isClickAllowed = true
    private var _binding: FragmentSettingFilterBinding? = null
    val binding: FragmentSettingFilterBinding
        get() = _binding!!

    var isDrawableChanged = false

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
        setupEventHandlers()

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etIndustryHint.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                findNavController().navigate(R.id.action_filterSettingFragment_to_choosingIndustryFragment)
            }
        }
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
            val bundle = Bundle()
            bundle.putString("working", binding.etWorkingPlaceHint.text.toString())
            bundle.putString("industry", binding.etIndustryHint.text.toString())
            bundle.putString("salary", binding.etSalaryHint.text.toString())
            bundle.putString("salaryClose", isDrawableChanged.toString())
            findNavController().navigate(R.id.action_filterSettingFragment_to_mainFragment, bundle)
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
}
