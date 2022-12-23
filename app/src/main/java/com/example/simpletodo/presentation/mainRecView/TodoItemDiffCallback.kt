package com.example.simpletodo.presentation.mainRecView

import androidx.recyclerview.widget.DiffUtil
import com.example.simpletodo.domain.TodoItem

class TodoItemDiffCallback: DiffUtil.ItemCallback<TodoItem>() {

    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }
}