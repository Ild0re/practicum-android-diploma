package ru.practicum.android.diploma.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.etSearch.text.isNotBlank()) {
                    binding.imageSearchOrClear.setImageResource(R.drawable.main_clear_icon)
                    // добавить debounce() как появятся интеракторы
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // в данный момент не используется
            }
        }
        binding.etSearch.addTextChangedListener(simpleTextWatcher)

        binding.imageSearchOrClear.setOnClickListener {
            binding.etSearch.setText("")
            if (binding.etSearch.text.isBlank()) {
                binding.imageSearchOrClear.setImageResource(R.drawable.search_icon)
            }
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // добавить debounce() как появятся интеракторы
                true
            }
            false
        }
    }
}
