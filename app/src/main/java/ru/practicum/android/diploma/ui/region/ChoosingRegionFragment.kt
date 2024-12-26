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
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoosingRegionBinding
import ru.practicum.android.diploma.domain.models.Region

class ChoosingRegionFragment : Fragment() {
    private var _binding: FragmentChoosingRegionBinding? = null
    private var adapter = RegionAdapter()
    val binding: FragmentChoosingRegionBinding
        get() = _binding!!

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

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        // временно для подключения РВ
        val region = arrayListOf(
            Region("1", "Москва"),
            Region("2", "Балашиха"),
            Region("3", "Верея")
        )
        showData(region)
        adapter.updateItems(region)
        binding.rvRegion.adapter = adapter
        binding.rvRegion.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()
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

    private fun showData(region: List<Region>) {
        if (region.isNotEmpty()) {
            binding.rvRegion.isVisible = true
        } else {
            binding.rvRegion.isVisible = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
    }
}
