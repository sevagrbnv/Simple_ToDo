package com.example.simpletodo.domain

data class TodoItem(
    val id: Int,
    val desc: String,
    val priority: String,
    val isComplete: Boolean
)
