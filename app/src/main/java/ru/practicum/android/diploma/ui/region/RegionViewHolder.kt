package ru.practicum.android.diploma.ui.region

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area

class RegionViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.regionName)
    private val endImage: ImageView = itemView.findViewById(R.id.region_image_endLine)
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
