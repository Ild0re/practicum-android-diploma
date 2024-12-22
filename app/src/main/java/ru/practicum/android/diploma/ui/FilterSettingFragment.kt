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
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingFilterBinding

class FilterSettingFragment : Fragment() {
    private var _binding: FragmentSettingFilterBinding? = null
    val binding: FragmentSettingFilterBinding
        get() = _binding!!

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
                }
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
                }
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
                }
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

        binding.salaryClose.setOnClickListener {
            binding.salaryClose.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.filter_square_activated_icon,
                0
            )
        }
        binding.reset.setOnClickListener {
            binding.etWorkingPlaceHint.setText("")
            binding.etIndustryHint.setText("")
            binding.etIndustryHint.setText("")
        }

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
}

