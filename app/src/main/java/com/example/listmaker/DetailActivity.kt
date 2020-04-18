package com.example.listmaker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.adapters.TaskListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {

    lateinit var taskList:TaskList
    lateinit var taskListRecyclerView:RecyclerView
    lateinit var addTaskButton:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        taskList = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY) as TaskList
        title = taskList.title

        taskListRecyclerView = findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter = TaskListAdapter(taskList)

        addTaskButton = findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }

    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, taskList)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)

        super.onBackPressed()
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(R.string.add_task_dialog_title)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) {
                dialog,_ ->
                val task = taskEditText.text.toString()

                addNewTask(task)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun addNewTask(task:String) {
        taskList.tasks.add(task)
        taskListRecyclerView.adapter?.notifyDataSetChanged()
    }
}
