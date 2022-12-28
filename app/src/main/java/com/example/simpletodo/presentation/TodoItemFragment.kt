package com.example.simpletodo.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpletodo.R
import com.example.simpletodo.databinding.FragmentTodoitemBinding
import com.example.simpletodo.presentation.MainActivity.Companion.ADD_MODE
import com.example.simpletodo.presentation.MainActivity.Companion.EDIT_MODE

class TodoItemFragment : Fragment() {

    private lateinit var viewModel: ItemViewModel
    private var onEditingFinishedListener: OnEditingFinishedListener? = null

    private lateinit var binding: FragmentTodoitemBinding

    private var screenMode = UNKNOWN_MODE
    private var itemId = com.example.simpletodo.domain.TodoItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        onEditingFinishedListener = null
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
        binding = FragmentTodoitemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
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
            binding.tilDesc.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener?.onEditingFinished()
        }
    }

    private fun startRightMode() {
        when(screenMode) {
            EDIT_MODE -> startEditMode()
            ADD_MODE -> startAddMode()
        }
    }

    private fun setTextChangeListener() {
        binding.edTextDesc.addTextChangedListener(object: TextWatcher {
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
            binding.edTextDesc.setText(it.desc)
            binding.cBoxPriority.isChecked = it.isHigh
        }

        binding.btnSave.setOnClickListener {
            viewModel.editTodoItem(
                binding.edTextDesc.text?.toString(),
                binding.cBoxPriority.isChecked)
        }
    }

    private fun startAddMode() {
        binding.btnSave.setOnClickListener {
            viewModel.addTodoItem(
                binding.edTextDesc.text?.toString(),
                binding.cBoxPriority.isChecked)
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
            itemId = args.getInt(ITEM_ID, com.example.simpletodo.domain.TodoItem.UNDEFINED_ID)
        }

    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    companion object {

        private const val SCREEN_MODE = "SCREEN_MODE"
        private const val UNKNOWN_MODE = ""
        private const val ITEM_ID = "ITEM_ID"

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