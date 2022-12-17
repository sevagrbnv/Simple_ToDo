package com.example.simpletodo.domain

data class TodoItem(
    val desc: String,
    val priority: String,
    val isComplete: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {

        const val UNDEFINED_ID = -1

    }
}
