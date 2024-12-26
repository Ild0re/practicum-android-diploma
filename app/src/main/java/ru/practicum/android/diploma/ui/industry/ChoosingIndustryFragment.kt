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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry

class ChoosingIndustryFragment : Fragment() {
    private var _binding: FragmentChoosingIndustryBinding? = null
    private var adapter = IndustryAdapter()
    val binding: FragmentChoosingIndustryBinding
        get() = _binding!!
    var isDrawableChanged = false

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
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        // временно для подключения РВ
        val industry = arrayListOf(
            Industry("1", "Авиаперевозки", false),
            Industry("2", "Автошкола", false),
            Industry("3", "Агрохимия", false),
        )
        showData(industry)
        adapter.updateItems(industry)
        binding.rvIndustry.adapter = adapter
        binding.rvIndustry.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()

        adapter.onItemClickListener = IndustryViewHolder.OnItemClickListener { item ->
            updateList(item, industry)
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
                    clickApply(itemIndustry)
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

    private fun clickApply(item: List<Industry>) {
        binding.btApply.setOnClickListener {
            for (i in item) {
                binding.etSearch.setText(i.name)
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

    private fun showData(industry: List<Industry>) {
        if (!industry.isEmpty()) {
            binding.rvIndustry.isVisible = true
        } else {
            binding.rvIndustry.isVisible = false
        }
    }
}

