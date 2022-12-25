package com.example.simpletodo.presentation.mainRecView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.simpletodo.R

class TodoListAdapter : ListAdapter<com.example.simpletodo.domain.TodoItem, TodoItemViewHolder>(TodoItemDiffCallback()) {

    var onCheckboxClickListener: ((com.example.simpletodo.domain.TodoItem) -> Unit)? = null
    var onTodoItemClickListener: ((com.example.simpletodo.domain.TodoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val layout = when (viewType) {
            ITEM_IS_COMPLETE -> R.layout.todoitem_disable
            ITEM_IS_COMPLETE_HIGH -> R.layout.todoitem_disable_high
            ITEM_IS_NOT_COMPLETE -> R.layout.todoitem_enable
            ITEM_IS_NOT_COMPLETE_HIGH -> R.layout.todoitem_enable_high
            else -> throw java.lang.RuntimeException("Unknown type of view: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent,false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.checkbox.setOnClickListener {
            onCheckboxClickListener?.invoke(todoItem)
        }
        holder.itemView.setOnClickListener {
            onTodoItemClickListener?.invoke(todoItem)
        }

        holder.desc.text = todoItem.desc
        holder.checkbox.isChecked = todoItem.isComplete
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (!item.isComplete && !item.isHigh) ITEM_IS_NOT_COMPLETE
            else if (!item.isComplete && item.isHigh) ITEM_IS_NOT_COMPLETE_HIGH
            else if (item.isComplete && !item.isHigh) ITEM_IS_COMPLETE
            else ITEM_IS_COMPLETE_HIGH
    }


    companion object {
        const val ITEM_IS_COMPLETE = 0
        const val ITEM_IS_NOT_COMPLETE = 1
        const val ITEM_IS_COMPLETE_HIGH = 2
        const val ITEM_IS_NOT_COMPLETE_HIGH = 3

        const val MAX_POOL_SIZE = 10
    }
}