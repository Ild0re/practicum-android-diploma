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
        for (i in industry) {
            if (i.id.equals(item.id)) {
                val clickFlag = !item.click
                val itemIndustry = listOf(Industry(item.id, item.name, clickFlag))
                if (itemIndustry.isEmpty()) {
                    binding.btApply.isVisible = false
                } else {
                    binding.btApply.isVisible = true
                    clickApply(itemIndustry)
                }
                adapter.updateItems(itemIndustry)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun clickApply(item: List<Industry>) {
        binding.btApply.setOnClickListener {
            for (i in item) {
                binding.etSearch.setText(i.name)
            }
        }
    }
}

