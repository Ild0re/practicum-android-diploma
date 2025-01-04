package ru.practicum.android.diploma.ui.industry

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.viewmodel.ChoosingIndustryViewModel
import ru.practicum.android.diploma.util.IndustryState

class ChoosingIndustryFragment : Fragment() {
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var _binding: FragmentChoosingIndustryBinding? = null
    private var adapter = IndustryAdapter()
    private var industryList = arrayListOf<Industry>()
    private var filteredList = arrayListOf<Industry>()

    val binding: FragmentChoosingIndustryBinding
        get() = _binding!!
    var isDrawableChanged = false

    private val viewModel by viewModel<ChoosingIndustryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoosingIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupEventHandlers()
        viewModel.loadData()

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustryState.Error -> {
                    showError()
                }

                is IndustryState.Empty -> {
                    showEmpty()
                }

                is IndustryState.Content -> {
                    showData(state.data)
                    industryList.addAll(state.data!!)
                }
            }
        }

        adapter.onItemClickListener = IndustryViewHolder.OnItemClickListener { item ->
            if (filteredList.isNotEmpty()) {
                updateList(item, filteredList)
            } else {
                updateList(item, industryList)
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

    private fun updateList(item: Industry, industry: ArrayList<Industry>) {
        if (!isDrawableChanged) {
            val existingItem = industry.find { it.id == item.id }

            if (existingItem != null) {
                val clickFlag = !item.click
                val itemIndustry = listOf(Industry(item.id, item.name, clickFlag))

                binding.btApply.isVisible = itemIndustry.isNotEmpty()
                if (itemIndustry.isNotEmpty()) {
                    clickApply(item)
                }
                adapter.updateItems(itemIndustry)
            }
        } else {
            adapter.updateItems(industry)
            binding.btApply.isVisible = false
        }

        adapter.notifyDataSetChanged()
        isDrawableChanged = !isDrawableChanged
    }

    private fun clickApply(item: Industry) {
        binding.btApply.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateFilterIndustry(item)
                delay(CLICK_DEBOUNCE_DELAY)
                findNavController().popBackStack()
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
                val query = p0.toString().lowercase()
                filteredList = industryList.filter { it.name.lowercase().contains(query) } as ArrayList<Industry>
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
        clearIndustry()
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // пока не используется
            }
            false
        }
    }

    private fun clearIndustry() {
        binding.imageSearchChoosing.setOnClickListener {
            binding.etSearch.setText("")
        }
    }

    private fun showData(country: List<Industry>?) {
        binding.rvIndustry.isVisible = true
        binding.ivIndustry.isVisible = false
        binding.tvIndustry.isVisible = false
        adapter.updateItems(country!!)
        binding.rvIndustry.adapter = adapter
        binding.rvIndustry.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.ivIndustry.isVisible = true
        binding.tvIndustry.isVisible = true
        binding.rvIndustry.isVisible = false
        binding.ivIndustry.setImageResource(R.drawable.main_image_empty)
        binding.tvIndustry.text = getString(R.string.tv_industry_no_found)
    }

    private fun showError() {
        binding.ivIndustry.isVisible = true
        binding.tvIndustry.isVisible = true
        binding.rvIndustry.isVisible = false
        binding.ivIndustry.setImageResource(R.drawable.region_not_found)
        binding.tvIndustry.text = getString(R.string.tv_region_empty)
    }
}

