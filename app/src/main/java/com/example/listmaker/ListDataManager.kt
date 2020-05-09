package com.example.listmaker

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel

class ListDataManager(
    application: Application
):AndroidViewModel(application) {
    private val context = application.applicationContext

    fun saveTaskList(taskList: TaskList) {
        var sharePrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharePrefs.putStringSet(taskList.title, taskList.tasks.toHashSet())
        sharePrefs.apply()
    }

    fun readTaskLists(): ArrayList<TaskList> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPrefs.all
        val tasksLists = ArrayList<TaskList>()
        for (taskList in contents) {
            val items = ArrayList(taskList.value as HashSet<String>)
            val newTaskList = TaskList(taskList.key, items)
            tasksLists.add(newTaskList)
        }

        return tasksLists
    }
}