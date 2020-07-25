package com.lukelorusso.toomuchofscrolling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_hexadecimal.view.*


class HexadecimalAdapter : RecyclerView.Adapter<HexadecimalAdapter.ViewHolder>() {
    var data: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hexadecimal,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].also { item ->
            holder.bind(item)
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) =
            with(itemView) {
                itemHexadecimalTextView.text = item
            }
    }
}
