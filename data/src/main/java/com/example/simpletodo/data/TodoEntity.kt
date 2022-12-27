package com.example.simpletodo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpletodo.data.TodoEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TodoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val desc: String,

    val isHigh: Boolean,

    val isComplete: Boolean
) {
    companion object {
        const val TABLE_NAME = "todo"
    }
}
