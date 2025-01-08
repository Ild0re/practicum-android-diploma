package ru.practicum.android.diploma.ui.region

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingRegionBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.viewmodel.ChoosingRegionViewModel
import ru.practicum.android.diploma.util.CountryState

class ChoosingRegionFragment : Fragment() {
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var _binding: FragmentChoosingRegionBinding? = null
    private var adapter = RegionAdapter()
    val binding: FragmentChoosingRegionBinding
        get() = _binding!!
    private val viewModel by viewModel<ChoosingRegionViewModel>()
    private var regionList = arrayListOf<Area>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoosingRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupEventHandlers()

        countryCheck()

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        adapter.onItemClickListener = RegionViewHolder.OnItemClickListener { item ->
            lifecycleScope.launch {
                viewModel.updateRegionFilter(item)
                delay(CLICK_DEBOUNCE_DELAY)
                findNavController().popBackStack()
            }
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
                    regionList.addAll(state.data!!)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTextWatcher() {
        val regionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (!p0.isNullOrEmpty()) {
                    binding.imageSearchChoosing.setImageResource(R.drawable.main_clear_icon)
                } else {
                    binding.imageSearchChoosing.setImageResource(R.drawable.search_icon)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                val query = p0.toString().lowercase()
                val filteredList = regionList.filter { it.name.lowercase().contains(query) }
                if (filteredList.isNotEmpty()) {
                    showData(filteredList)
                } else {
                    showEmpty()
                }
            }
        }
        binding.etSearch.addTextChangedListener(regionTextWatcher)
    }

    private fun setupEventHandlers() {
        clearRegion()
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // пока не используется
            }
            false
        }
    }

    private fun clearRegion() {
        binding.imageSearchChoosing.setOnClickListener {
            binding.etSearch.setText("")
        }
    }

    private fun showData(country: List<Area>?) {
        binding.rvRegion.isVisible = true
        binding.ivRegion.isVisible = false
        binding.tvRegion.isVisible = false
        adapter.updateItems(country!!)
        binding.rvRegion.adapter = adapter
        binding.rvRegion.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.ivRegion.isVisible = true
        binding.tvRegion.isVisible = true
        binding.rvRegion.isVisible = false
        binding.ivRegion.setImageResource(R.drawable.main_image_empty)
        binding.tvRegion.text = getString(R.string.tv_region_no_found)
    }

    private fun showError() {
        binding.ivRegion.isVisible = true
        binding.tvRegion.isVisible = true
        binding.rvRegion.isVisible = false
        binding.ivRegion.setImageResource(R.drawable.region_not_found)
        binding.tvRegion.text = getString(R.string.tv_region_empty)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
    }

    private fun countryCheck() {
        val countryCheck = viewModel.getFilter()
        if (countryCheck.country != null) {
            viewModel.loadData(countryCheck.country)
        } else {
            viewModel.getAllRegions()
        }
    }
}
