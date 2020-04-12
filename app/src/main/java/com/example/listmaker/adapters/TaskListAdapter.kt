package com.example.listmaker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.TaskListViewHolder
import com.example.listmaker.R
import com.example.listmaker.TaskList

class TaskListAdapter(var tasklist: TaskList) :
    RecyclerView.Adapter<TaskListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.task_list_view_holder,
            parent,
            false
        )

        return TaskListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasklist.tasks.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.itemTitle.text = tasklist.tasks[position]
    }
}