package ru.practicum.android.diploma.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.main.MainAdapter
import ru.practicum.android.diploma.ui.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.util.ScreenState

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    val binding: FragmentFavoriteBinding
        get() = _binding!!

    private val viewModel by viewModel<FavoriteViewModel>()

    private val vacanciesList = ArrayList<Vacancy>()
    private val vacanciesAdapter = MainAdapter(vacanciesList, ::onItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvVacancy.adapter = vacanciesAdapter
        viewModel.loadData()

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Empty -> showEmpty()
                is ScreenState.Content -> showData(state.data)
                is ScreenState.Loading -> {}
                is ScreenState.Error -> showError()
            }

        }
    }

    private fun onItemClickListener(vacancy: Vacancy) {
        val bundle = Bundle()
        bundle.putString("id", vacancy.id)
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
    }

    private fun showEmpty() {
        binding.tvFavorite.text = getString(R.string.tv_favorite_empty)
        binding.ivFavorite.setImageResource(R.drawable.favorite_image_empty)
        binding.ivFavorite.isVisible = true
        binding.rvVacancy.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showData(data: List<Vacancy>) {
        binding.ivFavorite.isVisible = false
        binding.rvVacancy.isVisible = true
        binding.tvFavorite.isVisible = false
        vacanciesList.clear()
        vacanciesList.addAll(data)
        vacanciesAdapter.notifyDataSetChanged()
    }

    private fun showError() {
        binding.ivFavorite.isVisible = true
        binding.rvVacancy.isVisible = false
        binding.ivFavorite.setImageResource(R.drawable.main_image_empty)
        binding.tvFavorite.text = getString(R.string.tv_main_vacancy_no_search)

    }
}
