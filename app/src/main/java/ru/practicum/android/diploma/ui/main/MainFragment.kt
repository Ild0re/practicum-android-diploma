package ru.practicum.android.diploma.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.ui.viewModel.MainViewModel
import ru.practicum.android.diploma.util.ScreenState

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() = _binding!!

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    var inputText=""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.etSearch.text.isNotBlank()) {
                    binding.imageSearchOrClear.setImageResource(R.drawable.main_clear_icon)
                    viewModel.searchDebounce(p0.toString(),10)
                    // убрать страницы
                } else {
                    inputText = p0.toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // в данный момент не используется
            }
        }
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when(state){
                is ScreenState.Loading -> showLoading()
                is ScreenState.Empty -> showEmpty()
                is ScreenState.Error -> showError()
                is ScreenState.Content -> {
                    // добавить вывод данных
                }
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
                viewModel.searchDebounce(inputText,10)
                // убрать страницы
                true
            }
            false
        }
    }
    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.rvVacancy.isVisible = false
        binding.tvMainEmptyOrNoConnect.isVisible = false
        binding.ivMainImage.isVisible = false
    }
    private fun showEmpty(){
        binding.tvMainEmptyOrNoConnect.text = getString(R.string.tv_main_empty)
        binding.ivMainImage.setImageResource(R.drawable.main_image_empty)
        binding.tvMainEmptyOrNoConnect.isVisible = true
        binding.progressBar.isVisible = false
        binding.rvVacancy.isVisible = false
    }
    private fun showError(){
        binding.tvMainEmptyOrNoConnect.text = getString(R.string.tv_main_no_connect)
        binding.ivMainImage.setImageResource(R.drawable.main_image_no_connect)
        binding.tvMainEmptyOrNoConnect.isVisible = true
        binding.progressBar.isVisible = false
        binding.rvVacancy.isVisible = false
    }
}
