package com.example.simpletodo.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.simpletodo.R
import com.example.simpletodo.domain.TodoItem

class ItemActivity : AppCompatActivity(), TodoItemFragment.OnEditingFinishedListener {

    private var screenMode = UNKNOWN_MODE
    private var itemId = TodoItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        parseIntent()
        if (savedInstanceState == null)
            startRightMode()
    }

    private fun startRightMode() {
    val fragment = when(screenMode) {
            EDIT_MODE -> TodoItemFragment.newInstanceEditItem(itemId)
            ADD_MODE -> TodoItemFragment.newInstanceAddItem()
        else -> throw RuntimeException("Unknown screen mode $screenMode")
    }
    supportFragmentManager.beginTransaction()
        .replace(R.id.item_container, fragment)
        .commit()
    }

    override fun onEditingFinished() {
        finish()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE))
            throw RuntimeException("Params of screen mode not found")
        val mode = intent.getStringExtra(SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE)
            throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode

        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(ITEM_ID))
                throw RuntimeException("Item id not found")
            itemId = intent.getIntExtra(ITEM_ID, TodoItem.UNDEFINED_ID)
        }

    }

    companion object {

        private const val SCREEN_MODE = "SCREEN_MODE"
        private const val ADD_MODE = "ADD_MODE"
        private const val EDIT_MODE = "EDIT_MODE"
        private const val ITEM_ID = "ITEM_ID"
        private const val UNKNOWN_MODE = ""

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEdit(context: Context, id: Int): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, EDIT_MODE)
            intent.putExtra(ITEM_ID, id)
            return intent
        }
    }
}