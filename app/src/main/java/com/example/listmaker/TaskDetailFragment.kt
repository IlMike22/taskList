package com.example.listmaker

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.adapters.TaskListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class TaskDetailFragment : Fragment() {

    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton
    lateinit var taskList:TaskList
    lateinit var listDataManager:ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)

        arguments?.let { bundle ->
            val args = TaskDetailFragmentArgs.fromBundle(bundle)
            taskList = listDataManager.readTaskLists().filter { list ->
                list.title == args.listString
            }[0]
        }
        activity?.apply {
            taskListRecyclerView = findViewById(R.id.task_list_recyclerview)
            taskListRecyclerView.layoutManager = LinearLayoutManager(this)
            taskListRecyclerView.adapter = TaskListAdapter(taskList)
            toolbar?.title = taskList.title

            addTaskButton = findViewById(R.id.add_task_button)
            addTaskButton.setOnClickListener {
                showCreateTaskDialog()
            }
        }

    }

    private fun showCreateTaskDialog() {
        activity?.apply {
            val taskEditText = EditText(this)
            taskEditText.inputType = InputType.TYPE_CLASS_TEXT

            AlertDialog.Builder(this)
                .setTitle(R.string.add_task_dialog_title)
                .setView(taskEditText)
                .setPositiveButton(R.string.add_task) { dialog, _ ->
                    val task = taskEditText.text.toString()

                    addNewTask(task)
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun addNewTask(task:String) {
        taskList.tasks.add(task)
        listDataManager.saveTaskList(taskList)
        taskListRecyclerView.adapter?.notifyDataSetChanged()
    }


    companion object {
        private val ARG_LIST = "list"
        fun newInstance(list:TaskList):TaskDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARG_LIST,list)
            val fragment = TaskDetailFragment()
            fragment.arguments = bundle
            return fragment

        }

    }

}
