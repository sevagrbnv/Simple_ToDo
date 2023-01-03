package com.example.simpletodo.presentation.mainRecView

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.simpletodo.R
import com.example.simpletodo.databinding.TodoitemDisableBinding
import com.example.simpletodo.databinding.TodoitemEnableBinding

class TodoListAdapter : ListAdapter<com.example.simpletodo.domain.TodoItem, TodoItemViewHolder>(TodoItemDiffCallback()) {

    var onCheckboxClickListener: ((com.example.simpletodo.domain.TodoItem) -> Unit)? = null
    var onTodoItemClickListener: ((com.example.simpletodo.domain.TodoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val layout = when (viewType) {
            ITEM_IS_COMPLETE -> R.layout.todoitem_disable
            ITEM_IS_NOT_COMPLETE -> R.layout.todoitem_enable
            else -> throw java.lang.RuntimeException("Unknown type of view: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return TodoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = getItem(position)
        val binding = holder.binding

        when (binding) {
            is TodoitemDisableBinding  -> {
                binding.todoItem = todoItem
                binding.cb.setOnClickListener { onCheckboxClickListener?.invoke(todoItem) }
                binding.root.setOnClickListener {
                    onTodoItemClickListener?.invoke(todoItem)
                }
                binding.tvDesc.paintFlags = binding.tvDesc.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                if (todoItem.isHigh)
                    binding.color.setBackgroundResource(R.color.red_high)
                else binding.color.setBackgroundResource(R.color.white)
            }
            is TodoitemEnableBinding -> {
                binding.todoItem = todoItem
                binding.cb.setOnClickListener { onCheckboxClickListener?.invoke(todoItem) }
                binding.root.setOnClickListener {
                    onTodoItemClickListener?.invoke(todoItem)
                }
                if (todoItem.isHigh)
                    binding.color.setBackgroundResource(R.color.red_high)
                else binding.color.setBackgroundResource(R.color.white)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (!item.isComplete) ITEM_IS_NOT_COMPLETE
            else ITEM_IS_COMPLETE
    }


    companion object {
        const val ITEM_IS_COMPLETE = 0
        const val ITEM_IS_NOT_COMPLETE = 1
        const val MAX_POOL_SIZE = 30
    }
}