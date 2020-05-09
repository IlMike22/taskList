package com.example.listmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_to_do_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [ToDoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ToDoListFragment : Fragment(), TodoListAdapter.ITodoListClickListener{

    lateinit var todoListRecyclerView: RecyclerView
    lateinit var todoListAdapter: TodoListAdapter
    lateinit var listDataManager:ListDataManager
    lateinit var taskLists:ArrayList<TaskList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.apply {
            listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)
        }


        taskLists = listDataManager.readTaskLists()
        todoListRecyclerView = view.findViewById(R.id.todolists_recyclerview)
        todoListRecyclerView.layoutManager = LinearLayoutManager(activity)
        todoListRecyclerView.adapter = TodoListAdapter(taskLists, this )

        fab.setOnClickListener { _ ->
            todoListFragment.todoListAdapter = todolists_recyclerview.adapter as TodoListAdapter
            createNewListDialog()
        }

    }

    interface OnFragmentInteractionListener {
        fun onTodoListClicked(list:TaskList)
    }

    companion object {
        fun newInstance():ToDoListFragment {
            return ToDoListFragment()
        }
    }

    override fun onTodoItemClicked(taskList: TaskList) {
        openTaskList(taskList)
    }

    fun addList(taskList:TaskList) {
        listDataManager.saveTaskList(taskList)
        val adapter = todolists_recyclerview.adapter as TodoListAdapter
        adapter.addList(taskList)
    }

    fun saveList(taskList:TaskList) {
        listDataManager.saveTaskList(taskList)
        updateLists()
    }

    private fun updateLists() {
        val taskLists = listDataManager.readTaskLists()
        todolists_recyclerview.adapter = TodoListAdapter(taskLists, this)
    }

    private fun createNewListDialog() {
        activity?.apply {
            val title = getString(R.string.create_list_dialog_title)
            val positiveButtonTitle = getString(R.string.create_list_dialog_button_title)
            val dialog = AlertDialog.Builder(this)
            val editTextTodoTitle = EditText(this)
            editTextTodoTitle.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            dialog.setTitle(title)
            dialog.setView(editTextTodoTitle)
            dialog.setPositiveButton(positiveButtonTitle) { dialog, _ ->
                val userInput = editTextTodoTitle.text.toString()
                val taskList = TaskList(title = userInput)
                addList(taskList)
                storeValueInSharedPrefs(taskList)

                updateList(taskList)

                dialog.dismiss()

                openTaskList(taskList)
            }
            dialog.create().show()
        }
    }
    private fun updateList(taskList:TaskList) {
        //if (::totodoListAdapter.isInitialized)
        todoListFragment.todoListAdapter.addList(taskList)

    }

    private fun storeValueInSharedPrefs(taskList:TaskList) {
        todoListFragment.listDataManager.saveTaskList(taskList)
    }

    private fun openTaskList(tasks:TaskList) {
        view?.apply {
            val action = ToDoListFragmentDirections.actionToDoListFragmentToTaskDetailFragment(tasks.title)
            findNavController().navigate(action)
        }
    }


}
