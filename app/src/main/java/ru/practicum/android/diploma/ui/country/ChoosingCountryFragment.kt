package ru.practicum.android.diploma.ui.country

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingCountryBinding
import ru.practicum.android.diploma.domain.models.Country

class ChoosingCountryFragment : Fragment() {
    private var _binding: FragmentChoosingCountryBinding? = null
    val adapter = CountryAdapter()
    val binding: FragmentChoosingCountryBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoosingCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        val country = arrayListOf(
            Country("1", "Оренбург"),
            Country("2", "Самара"),
            Country("3", "Челябинск"),
        )
        adapter.updateItems(country)
        showData(country)
        binding.rvCountry.adapter = adapter
        binding.rvCountry.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()
        adapter.onItemClickListener = CountryViewHolder.OnItemClickListener { item ->
            // положить в SP
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
    }

    private fun showData(country: List<Country>) {
        if (!country.isEmpty()) {
            binding.rvCountry.isVisible = true
        } else {
            binding.rvCountry.isVisible = false
        }
    }
}
