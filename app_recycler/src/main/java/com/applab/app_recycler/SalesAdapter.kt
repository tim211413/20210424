package com.applab.app_recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*

class SalesAdapter (val list: List<Map<String, Object>>) : RecyclerView.Adapter<SalesAdapter.SalesHolder>() {
    private lateinit var context: Context

    class SalesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val region: TextView = itemView.text_region
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(
            R.layout.item, parent, false
        )
        return SalesHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SalesHolder, position: Int) {
        val currentItem = list[position]
        holder.region.text = position.toString() + " : " + currentItem["Region"].toString()
        holder.region.setOnClickListener {
            Toast.makeText(context, list[position].toString(), Toast.LENGTH_SHORT).show()
        }
    }
}