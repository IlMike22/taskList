package com.example.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(
    private val taskLists:ArrayList<TaskList>,
    val clickListener:ITodoListClickListener
):RecyclerView.Adapter<TodoListViewHolder>() {

    interface ITodoListClickListener {
        fun onTodoItemClicked(taskList:TaskList)
    }

    fun addList(taskList:TaskList) {
        taskLists.add(taskList)
        notifyItemInserted(taskLists.size-1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item_view_holder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskLists.size
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text = taskLists[position].title
        holder.itemView.setOnClickListener {
            clickListener.onTodoItemClicked(taskLists[position])
        }
    }
}