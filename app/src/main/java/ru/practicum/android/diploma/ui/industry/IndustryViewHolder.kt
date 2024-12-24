package ru.practicum.android.diploma.ui.industry

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry


class IndustryViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.industryName)
    private val endImage: ImageView = itemView.findViewById(R.id.image_endLine)
    fun bind(item: Industry, onItemClickListener: OnItemClickListener?) {
        Log.d("Sprint 26", "$item")
        tvName.text = item.name
        if (item.click == false) {
            endImage.setImageResource(R.drawable.circle_disable_icon)
        } else {
            endImage.setImageResource(R.drawable.circle_activated_icon)
        }

        itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: Industry)
    }
}
