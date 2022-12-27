package com.example.simpletodo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert(entity = TodoEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todoEntity: TodoEntity)

    @Query("DELETE FROM todo WHERE id = (:todoEntityId)")
    suspend fun deleteTodo(todoEntityId: Int)

    @Query("SELECT * FROM todo WHERE id = (:todoEntityId) LIMIT 1")
    suspend fun getTodo(todoEntityId: Int): TodoEntity

    @Query("SELECT * FROM todo")
    fun getTodoList(): LiveData<List<TodoEntity>>
}