package com.example.simpletodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import com.example.simpletodo.R
import com.example.simpletodo.presentation.MainFragment.MainFragment
import com.example.simpletodo.presentation.TodoItemFragment.TodoItemFragment

class MainActivity : AppCompatActivity(),
    TodoItemFragment.OnEditingFinishedListener,
    MainFragment.OpenSecondFragmentListener {

    private val component by lazy {
        (application as App).component
    }

    private var todoItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

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
        replaceWithPopBackStack(R.id.main_container, MainFragment())
        hideSecondFragment()
    }

    private fun showSecondFragment() {
        setVisibleOfSecondFragment(true)
    }

    private fun hideSecondFragment() {
        setVisibleOfSecondFragment(false)
    }

    private fun setVisibleOfSecondFragment(state: Boolean) {
        if (!isOnePaneMode()) {
            val container = findViewById<FragmentContainerView>(R.id.todoitem_container)
            val textIfIsEmpty = findViewById<TextView>(R.id.textSecondFragmentIsEmpty)
            container.isVisible = state
            textIfIsEmpty.isVisible = !state
        }
    }

    private fun isOnePaneMode(): Boolean {
        return todoItemContainer == null
    }

    override fun openSecondFragment(mode: String, itemId: Int) {

        val fragment = when (mode) {
            ADD_MODE -> TodoItemFragment.newInstanceAddItem()
            EDIT_MODE -> TodoItemFragment.newInstanceEditItem(itemId)
            else -> throw RuntimeException("Unknown type of mode $mode")
        }

        val currentContainer = if (isOnePaneMode()) R.id.main_container
            else R.id.todoitem_container
        showSecondFragment()
        replaceWithPopBackStack(currentContainer, fragment)
    }

    private fun replaceWithPopBackStack(container: Int, fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(container, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .commit()
    }

    companion object {
        val ADD_MODE = "ADD_MODE"
        val EDIT_MODE = "EDIT_MODE"
    }
}