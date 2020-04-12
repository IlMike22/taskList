package com.example.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.adapters.TaskListAdapter

class DetailActivity : AppCompatActivity() {

    lateinit var taskList:TaskList
    lateinit var taskListRecyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        taskList = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY) as TaskList
        title = taskList.title

        taskListRecyclerView = findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter = TaskListAdapter(taskList)

    }
}
