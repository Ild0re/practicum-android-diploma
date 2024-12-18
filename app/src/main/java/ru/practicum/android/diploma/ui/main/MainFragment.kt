package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.ui.viewmodel.MainViewModel
import ru.practicum.android.diploma.util.ScreenState

class MainFragment : Fragment() {

    companion object {
        const val SERVER_ERROR = "Ошибка сервера"
        private const val ZERO = 0
    }

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() = _binding!!

    private val viewModel by viewModel<MainViewModel>()

    private val vacanciesList = ArrayList<VacancyDto>()

    // заменить VacancyDto на Vacancy
    private val vacanciesAdapter = MainAdapter(vacanciesList, ::onItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    var inputText = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvVacancy.adapter = vacanciesAdapter

        binding.rvVacancy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos = (binding.rvVacancy.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacanciesAdapter.itemCount
                    if (itemsCount > 0 && pos >= itemsCount - 1) {
                        viewModel.searchVacancies(inputText)
                    }
                }
            }
        })

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.etSearch.text.isNotBlank()) {
                    binding.imageSearchOrClear.setImageResource(R.drawable.main_clear_icon)
                    viewModel.searchDebounce(p0.toString())
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
            when (state) {
                is ScreenState.Loading -> showLoading()
                is ScreenState.Empty -> showEmpty()
                is ScreenState.Error -> showError(state.message)
                is ScreenState.Content -> {
                    showData(state.data)
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
            inputMethodManager?.hideSoftInputFromWindow(binding.etSearch.windowToken, ZERO)
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.loadData(binding.etSearch.text.toString(), ZERO)
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

    private fun showEmpty() {
        binding.tvMainEmptyOrNoConnect.text = getString(R.string.tv_main_empty)
        binding.ivMainImage.setImageResource(R.drawable.main_image_empty)
        binding.tvMainEmptyOrNoConnect.isVisible = true
        binding.ivMainImage.isVisible = true
        binding.progressBar.isVisible = false
        binding.rvVacancy.isVisible = false
    }

    private fun showError(text: String) {
        binding.tvMainEmptyOrNoConnect.isVisible = true
        binding.ivMainImage.isVisible = true
        binding.progressBar.isVisible = false
        binding.rvVacancy.isVisible = false
        if (text == SERVER_ERROR) {
            binding.tvMainEmptyOrNoConnect.text = getString(R.string.tv_main_error_server)
            binding.ivMainImage.setImageResource(R.drawable.main_image_error_server)
        } else {
            binding.tvMainEmptyOrNoConnect.text = getString(R.string.tv_main_no_connect)
            binding.ivMainImage.setImageResource(R.drawable.main_image_no_connect)
        }
    }

    // заменить VacancyDto на Vacancy
    @SuppressLint("NotifyDataSetChanged")
    private fun showData(data: List<VacancyDto>) {
        binding.progressBar.isVisible = false
        binding.ivMainImage.isVisible = false
        binding.rvVacancy.isVisible = true
        vacanciesList.clear()
        vacanciesList.addAll(data)
        vacanciesAdapter.notifyDataSetChanged()
    }

    private fun onItemClickListener(vacancy: VacancyDto) {
        // заменить VacancyDto а модель
        val bundle = Bundle()
        bundle.putString("id", vacancy.id)
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
    }
}
