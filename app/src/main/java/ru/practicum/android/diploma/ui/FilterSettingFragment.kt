package ru.practicum.android.diploma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.industry.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingFragment_to_choosingIndustryFragment)
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
}

