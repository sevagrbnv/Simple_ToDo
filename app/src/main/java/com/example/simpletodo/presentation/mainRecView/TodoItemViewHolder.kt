package com.example.simpletodo.presentation.mainRecView

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R

class TodoItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val desc = view.findViewById<TextView>(R.id.tv_desc)
    val checkbox = view.findViewById<CheckBox>(R.id.cb)
}