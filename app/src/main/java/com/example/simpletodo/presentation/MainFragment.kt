package com.example.simpletodo.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.presentation.mainRecView.TodoListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment() {

    private var openSecondFragmentListener: OpenSecondFragmentListener? = null

    private lateinit var viewModel: MainViewModel
    private lateinit var todoListAdapter: TodoListAdapter

    private lateinit var textIfEmpty: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenSecondFragmentListener) {
            openSecondFragmentListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        openSecondFragmentListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.todoList.observe(viewLifecycleOwner) {
            todoListAdapter.submitList(it)
        }
        setRecView(view)
        val btnAddItem = view.findViewById<FloatingActionButton>(R.id.fab)
        btnAddItem.setOnClickListener {
            openSecondFragmentListener?.openSecondFragment(ADD_MODE, NEEDLES_KEY)
        }
    }

    private fun setRecView(view: View) {
        val rcViewTodoList = view.findViewById<RecyclerView>(R.id.recView)
        textIfEmpty = view.findViewById(R.id.textIfEmptyList)
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
        checkListForEmpty(rcViewTodoList)
    }

    private fun checkListForEmpty(rcView: RecyclerView) {
        if (viewModel.isEmptyList()) {
            rcView.visibility = View.INVISIBLE
            textIfEmpty.visibility = View.VISIBLE
        }
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
                checkListForEmpty(rcViewTodoList)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcViewTodoList)
    }

    private fun setItemClickListener() {
        todoListAdapter.onTodoItemClickListener = {
            openSecondFragmentListener?.openSecondFragment(EDIT_MODE, it.id)
        }
    }

    private fun setCheckboxListener() {
        todoListAdapter.onCheckboxClickListener = {
            viewModel.changeItemCompleteState(it)
        }
    }

    interface OpenSecondFragmentListener {

        fun openSecondFragment(mode: String, itemId: Int)
    }

    companion object {

        private val NEEDLES_KEY = -1
        private val ADD_MODE = "ADD_MODE"
        private val EDIT_MODE = "EDIT_MODE"
    }
}