package ru.practicum.android.diploma.ui.industry

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

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        // временно для подключения РВ
        val industry = arrayListOf(
            Industry("1", "Авиаперевозки", false),
            Industry("2", "Автошкола", false),
            Industry("3", "Агрохимия", false),
        )
        adapter.updateItems(industry)
        binding.rvIndustry.adapter = adapter
        binding.rvIndustry.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()

        adapter.onItemClickListener = IndustryViewHolder.OnItemClickListener { item ->
            updateList(item, industry)
        }
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
}

