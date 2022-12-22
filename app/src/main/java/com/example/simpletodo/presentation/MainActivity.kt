package com.example.simpletodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.presentation.recView.TodoListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), TodoItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var todoListAdapter: TodoListAdapter
    private var todoItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoItemContainer = findViewById(R.id.todoitem_container)
        setRecView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.todoList.observe(this) {
            todoListAdapter.submitList(it)
        }
        val btnAddItem = findViewById<FloatingActionButton>(R.id.fab)
        btnAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ItemActivity.newIntentAdd(this)
                startActivity(intent)
            } else {
                startSecondFragment(TodoItemFragment.newInstanceAddItem())
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun startSecondFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.todoitem_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean {
        return todoItemContainer == null
    }

    private fun setRecView() {
        val rcViewTodoList = findViewById<RecyclerView>(R.id.recView)
        todoListAdapter = TodoListAdapter()
        with(rcViewTodoList) {
            adapter = todoListAdapter
            recycledViewPool.setMaxRecycledViews(
                TodoListAdapter.ITEM_IS_COMPLETE,
                TodoListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(
                TodoListAdapter.ITEM_IS_NOT_COMPLETE,
                TodoListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(
                TodoListAdapter.ITEM_IS_COMPLETE_HIGH,
                TodoListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(
                TodoListAdapter.ITEM_IS_NOT_COMPLETE_HIGH,
                TodoListAdapter.MAX_POOL_SIZE)
        }

        setCheckboxListener()
        setItemClickListener()
        setSwipeListener(rcViewTodoList)
    }

    private fun setSwipeListener(rcViewTodoList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = todoListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteTodoItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcViewTodoList)
    }

    private fun setItemClickListener() {
        todoListAdapter.onTodoItemClickListener = {
            if (isOnePaneMode()) {
                val intent = ItemActivity.newIntentEdit(this, it.id)
                startActivity(intent)
            } else {
                startSecondFragment(TodoItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setCheckboxListener() {
        todoListAdapter.onCheckboxClickListener = {
            viewModel.changeItemCompleteState(it)
        }
    }


}