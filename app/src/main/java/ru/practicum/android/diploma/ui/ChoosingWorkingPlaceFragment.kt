package ru.practicum.android.diploma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingWorkingPlaceBinding
import ru.practicum.android.diploma.ui.viewmodel.ChoosingWorkingPlaceViewModel

class ChoosingWorkingPlaceFragment : Fragment() {

    private var _binding: FragmentChoosingWorkingPlaceBinding? = null
    private var region: String = ""
    private var country: String = ""
    private val viewModel by viewModel<ChoosingWorkingPlaceViewModel>()
    val binding: FragmentChoosingWorkingPlaceBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoosingWorkingPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCountries()
        setFilters()
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etCountryHint.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                findNavController().navigate(R.id.action_choosingWorkingPlaceFragment_to_choosingCountryFragment)
            }
        }
        binding.etRegionHint.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                findNavController().navigate(R.id.action_choosingWorkingPlaceFragment_to_choosingRegionFragment)
            }
        }
        binding.apply.setOnClickListener {
            findNavController().navigate(R.id.action_choosingWorkingPlaceFragment_to_filterSettingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCountries()
        setFilters()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
    }

    private fun getRegionText() {
        arguments?.let { bundle ->
            region = bundle.getString("region", "")
            binding.etRegionHint.setText(region)
        }
    }

    private fun getCountryText() {
        arguments?.let { bundle ->
            country = bundle.getString("country", "")
            binding.etCountryHint.setText(country)
        }
    }

    private fun setFilters() {
        getRegionText()
        getCountryText()
        val filter = viewModel.getFilter()
        binding.etCountryHint.setText(filter.country?.name)
        binding.etRegionHint.setText(filter.area?.name)
        if (binding.etCountryHint.text.isNullOrEmpty() && binding.etRegionHint.text.isNullOrEmpty()) {
            binding.apply.isVisible = false
        } else {
            binding.apply.isVisible = true
        }
        lifecycleScope.launch {
            viewModel.countryListNew.collect { country ->
                if (filter.area != null && country != null) {
                    for (i in country) {
                        if (filter.area.parentId == i.id) {
                            binding.etCountryHint.setText(i.name)
                            viewModel.updateCountryFilter(i)
                        }
                    }
                }
            }
        }
    }
}
