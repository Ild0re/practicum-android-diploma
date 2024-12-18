package ru.practicum.android.diploma.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentDetailedInformationBinding
import ru.practicum.android.diploma.ui.viewmodel.DetailedInformationViewModel

class DetailsFragment : Fragment() {
    private val viewModel by viewModel<DetailedInformationViewModel>()
    private var _binding: FragmentDetailedInformationBinding? = null
    val binding: FragmentDetailedInformationBinding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailedInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        // Делаем кликер для кнопки "Добавить в избранное"
        binding.lickedIcon.setOnClickListener { }

        // Делаем кликер для кнопки "Поделиться"
        binding.shareButton.setOnClickListener {
            viewModel.shareVacancyUrl()
        }
    }
}
