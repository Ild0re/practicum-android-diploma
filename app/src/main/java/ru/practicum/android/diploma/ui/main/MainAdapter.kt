package ru.practicum.android.diploma.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class MainAdapter(
    private var data: List<Vacancy>,
    private val onItemClickListener: (Vacancy) -> Unit,
) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return MainViewHolder(VacancyItemBinding.inflate(layoutInspector, parent, false), parent.context)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val vacancy = data[position]
        holder.bind(vacancy)
        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(vacancy)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
