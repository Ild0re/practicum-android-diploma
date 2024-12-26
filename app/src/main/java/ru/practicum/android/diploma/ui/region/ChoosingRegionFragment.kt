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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingRegionBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.viewmodel.ChoosingRegionViewModel
import ru.practicum.android.diploma.util.CountryState

class ChoosingRegionFragment : Fragment() {
    private var _binding: FragmentChoosingRegionBinding? = null
    private var adapter = RegionAdapter()
    val binding: FragmentChoosingRegionBinding
        get() = _binding!!
    private val viewModel by viewModel<ChoosingRegionViewModel>()

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

        // заглушка страны

        val subArea1 = Area(id = "1", name = "SubArea 1", url = "http://example.com/subarea1", areas = emptyList())
        val subArea2 = Area(id = "2", name = "SubArea 2", url = "http://example.com/subarea2", areas = emptyList())
        val country = Area(
            id = "40",
            name = "Main Area",
            url = "http://example.com/mainarea",
            areas = listOf(subArea1, subArea2)
        )
        viewModel.loadData(country)

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
                // в данный момент не используется
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
}
