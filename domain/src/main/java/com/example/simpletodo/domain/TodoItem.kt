package com.example.simpletodo.domain

data class TodoItem(
    val desc: String,
    val isComplete: Boolean,
    val isHigh: Boolean = false,
    var id: Int = UNDEFINED_ID
) {
    companion object {

        const val UNDEFINED_ID = -1

    }
}
