package com.applab.app_parking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class SalesAdapter (val list: List<Map<String, Object>>) : RecyclerView.Adapter<SalesAdapter.SalesHolder>() {
    lateinit var context: Context

    class SalesHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val parkName: TextView = itemView.text_parkName
        val totalSpace: TextView = itemView.text_totalSpace
        val surplusSpace: TextView = itemView.text_surplusSpace

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesAdapter.SalesHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(
            R.layout.item, parent, false
        )
        return SalesHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SalesAdapter.SalesHolder, position: Int) {
        val currentItem = list[position]
        holder.parkName.text = position.toString() + " : " + currentItem["parkName"].toString()
        holder.surplusSpace.text = currentItem["surplusSpace"].toString()
        holder.totalSpace.text = currentItem["totalSpace"].toString().toDouble().toInt().toString()

        holder.parkName.setOnClickListener {
            Toast.makeText(context, list[position].toString(), Toast.LENGTH_SHORT).show()
        }
    }


}