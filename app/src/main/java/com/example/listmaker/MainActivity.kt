package com.example.listmaker

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    lateinit var todoList: RecyclerView
    lateinit var todoListAdapter: TodoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        todoList = findViewById(R.id.todolists_recyclerview)
        todoList.layoutManager = LinearLayoutManager(this)
        todoList.adapter = TodoListAdapter()

        fab.setOnClickListener { _ ->
            todoListAdapter = todolists_recyclerview.adapter as TodoListAdapter
            createNewListDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createNewListDialog() {
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
            updateList(userInput)
            dialog.dismiss()
        }
        dialog.create().show()

    }

    private fun updateList(newListItem:String) {
        if (::todoListAdapter.isInitialized)
            todoListAdapter.addItem(newListItem)
    }
}
