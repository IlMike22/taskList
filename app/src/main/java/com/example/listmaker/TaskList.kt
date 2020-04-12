package com.example.listmaker

import android.os.Parcel
import android.os.Parcelable

class TaskList(val title: String, val tasks: ArrayList<String> = ArrayList<String>()) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeStringList(tasks)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<TaskList> {
        override fun createFromParcel(source: Parcel) = TaskList(source)

        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)

    }
}