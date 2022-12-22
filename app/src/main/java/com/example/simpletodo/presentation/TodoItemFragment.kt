package com.example.simpletodo.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpletodo.R
import com.example.simpletodo.domain.TodoItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TodoItemFragment : Fragment() {

    private lateinit var viewModel: ItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilDesc: TextInputLayout
    private lateinit var edTextDesc: TextInputEditText
    private lateinit var cBoxPriority: CheckBox
    private lateinit var bntSave: Button

    private var screenMode = UNKNOWN_MODE
    private var itemId = TodoItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todoitem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        initViews(view)
        setTextChangeListener()
        startRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputDesc.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_empty_line)
            } else {
                null
            }
            tilDesc.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun startRightMode() {
        when(screenMode) {
            EDIT_MODE -> startEditMode()
            ADD_MODE -> startAddMode()
        }
    }

    private fun setTextChangeListener() {
        edTextDesc.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputDesc()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun startEditMode() {
        viewModel.getTodoItem(itemId)
        viewModel.todoItem.observe(viewLifecycleOwner) {
            edTextDesc.setText(it.desc)
            cBoxPriority.isChecked = it.isHigh
        }

        bntSave.setOnClickListener {
            viewModel.editTodoItem(edTextDesc.text?.toString(), cBoxPriority.isChecked)
        }
    }

    private fun startAddMode() {
        bntSave.setOnClickListener {
            viewModel.addTodoItem(edTextDesc.text?.toString(), cBoxPriority.isChecked)
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE))
            throw RuntimeException("Params of screen mode not found")
        val mode = args.getString(SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE)
            throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode

        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(ITEM_ID))
                throw RuntimeException("Item id not found")
            itemId = args.getInt(ITEM_ID, TodoItem.UNDEFINED_ID)
        }

    }

    private fun initViews(view: View) {
        tilDesc = view.findViewById(R.id.til_desc)
        edTextDesc = view.findViewById(R.id.edTextDesc)
        cBoxPriority = view.findViewById(R.id.cBoxPriority)
        bntSave = view.findViewById(R.id.btn_save)
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    companion object {

        private const val SCREEN_MODE = "SCREEN_MODE"
        private const val ADD_MODE = "ADD_MODE"
        private const val EDIT_MODE = "EDIT_MODE"
        private const val ITEM_ID = "ITEM_ID"
        private const val UNKNOWN_MODE = ""

        fun newInstanceAddItem(): TodoItemFragment {
            return TodoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditItem(todoItemId: Int): TodoItemFragment {

            return TodoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, EDIT_MODE)
                    putInt(ITEM_ID, todoItemId)
                }
            }
        }
    }
}