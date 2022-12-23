package com.example.simpletodo.presentation.mainRecView

import androidx.recyclerview.widget.DiffUtil
import com.example.simpletodo.domain.TodoItem

class TodoListDiffCallback (
    private val oldList: List<TodoItem>,
    private val newList: List<TodoItem>
    ): DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}