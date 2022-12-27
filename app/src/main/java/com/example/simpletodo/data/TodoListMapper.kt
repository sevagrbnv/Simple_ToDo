package com.example.simpletodo.data

import com.example.simpletodo.domain.TodoItem

class TodoListMapper {

    fun mapItemToEntity(todoItem: TodoItem) = TodoEntity(
        id = todoItem.id,
        desc = todoItem.desc,
        isHigh = todoItem.isHigh,
        isComplete = todoItem.isComplete
    )

    fun mapEntityToItem(todoEntity: TodoEntity) = TodoItem(
        id = todoEntity.id,
        desc = todoEntity.desc,
        isHigh = todoEntity.isHigh,
        isComplete = todoEntity.isComplete
    )

    fun mapListEntityToListItem(list: List<TodoEntity>) = list.map {
        mapEntityToItem(it)
    }
}