package ru.practicum.android.diploma.ui.country

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area

class CountryViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.countryName)
    private val endImage: ImageView = itemView.findViewById(R.id.country_image_endLine)
    fun bind(item: Area, onItemClickListener: OnItemClickListener?) {
        tvName.text = item.name
        endImage.setImageResource(R.drawable.filter_arrow_right_icon)

        itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: Area)
    }
}
