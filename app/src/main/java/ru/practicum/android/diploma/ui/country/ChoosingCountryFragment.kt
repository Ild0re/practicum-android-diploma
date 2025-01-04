package ru.practicum.android.diploma.ui.country

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingCountryBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.viewmodel.ChoosingCountryViewModel
import ru.practicum.android.diploma.util.CountryState

class ChoosingCountryFragment : Fragment() {
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var _binding: FragmentChoosingCountryBinding? = null
    val adapter = CountryAdapter()
    val binding: FragmentChoosingCountryBinding
        get() = _binding!!

    private val viewModel by viewModel<ChoosingCountryViewModel>()

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
        viewModel.loadData()

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Error -> {
                    showError()
                }

                is CountryState.Empty -> {
                    showEmpty()
                }

                is CountryState.Content -> {
                    showData(state.data)

                }
            }
        }

        adapter.onItemClickListener = CountryViewHolder.OnItemClickListener { item ->
            lifecycleScope.launch {
                viewModel.updateCountryFilter(item)
                delay(CLICK_DEBOUNCE_DELAY)
                findNavController().popBackStack()
            }
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

    private fun showData(country: List<Area>?) {
        binding.rvCountry.isVisible = true
        binding.ivCountry.isVisible = false
        binding.tvCountry.isVisible = false
        adapter.updateItems(country!!)
        binding.rvCountry.adapter = adapter
        binding.rvCountry.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.ivCountry.isVisible = true
        binding.tvCountry.isVisible = true
        binding.rvCountry.isVisible = false
        binding.ivCountry.setImageResource(R.drawable.main_image_empty)
        binding.tvCountry.text = getString(R.string.tv_country_no_found)
    }

    private fun showError() {
        binding.ivCountry.isVisible = true
        binding.tvCountry.isVisible = true
        binding.rvCountry.isVisible = false
        binding.ivCountry.setImageResource(R.drawable.region_not_found)
        binding.tvCountry.text = getString(R.string.tv_region_empty)
    }
}
