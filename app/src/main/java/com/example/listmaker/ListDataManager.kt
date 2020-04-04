package com.example.listmaker

import android.content.Context
import android.preference.PreferenceManager

class ListDataManager(private val context: Context) {
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