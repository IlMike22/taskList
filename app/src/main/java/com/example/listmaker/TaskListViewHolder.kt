package com.example.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListViewHolder(detailView: View) : RecyclerView.ViewHolder(detailView) {
    val itemTitle = detailView.findViewById<TextView>(R.id.tv_task)
}