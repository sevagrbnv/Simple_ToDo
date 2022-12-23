package com.example.simpletodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentContainerView
import com.example.simpletodo.R

class MainActivity : AppCompatActivity(),
    TodoItemFragment.OnEditingFinishedListener,
    MainFragment.OpenSecondFragmentListener {

    private var todoItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoItemContainer = findViewById(R.id.todoitem_container)
        if (savedInstanceState == null)
            setMainFragmentContainer()
    }

    private fun setMainFragmentContainer() {
        val fragment = MainFragment()
        if (isOnePaneMode())
            supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, fragment)
            .commit()
    }

    override fun onEditingFinished() {
        val fragment = MainFragment()
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean {
        return todoItemContainer == null
    }

    override fun openSecondFragment(mode: String, itemId: Int) {

        val fragment = when (mode) {
            "ADD" -> TodoItemFragment.newInstanceAddItem()
            "EDIT" -> TodoItemFragment.newInstanceEditItem(itemId)
            else -> throw RuntimeException("Unknown type of mode $mode")
        }
        Log.d("single", "openSecondFragment $mode")
        val currentContainer = if (isOnePaneMode()) R.id.main_container
            else R.id.todoitem_container
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(currentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}