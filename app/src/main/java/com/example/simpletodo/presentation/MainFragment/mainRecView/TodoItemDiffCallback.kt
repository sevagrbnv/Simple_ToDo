package com.example.simpletodo.presentation.MainFragment.mainRecView

import androidx.recyclerview.widget.DiffUtil

class TodoItemDiffCallback: DiffUtil.ItemCallback<com.example.simpletodo.domain.TodoItem>() {

    override fun areItemsTheSame(oldItem: com.example.simpletodo.domain.TodoItem, newItem: com.example.simpletodo.domain.TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: com.example.simpletodo.domain.TodoItem, newItem: com.example.simpletodo.domain.TodoItem): Boolean {
        return oldItem == newItem
    }
}