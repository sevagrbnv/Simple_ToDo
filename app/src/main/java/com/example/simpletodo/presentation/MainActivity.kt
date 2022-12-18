package com.example.simpletodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.simpletodo.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.todoList.observe(this) {
            Log.d("Main Activity Log", it.toString())
            if (count == 0) {
                count++
                val item = it[0]
                viewModel.changeItemCompleteState(item)
            }
        }
    }
}