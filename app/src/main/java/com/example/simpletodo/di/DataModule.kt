package com.example.simpletodo.di

import android.app.Application
import com.example.simpletodo.data.TodoDao
import com.example.simpletodo.data.TodoDatabase
import com.example.simpletodo.data.TodoListRepositoryImpl
import com.example.simpletodo.domain.TodoListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindTodoListRepository(impl: TodoListRepositoryImpl): TodoListRepository

    companion object {

        @AppScope
        @Provides
        fun provideTodoDao(
            application: Application
        ): TodoDao {
            return TodoDatabase.getInstance(application).todoListDao()
        }
    }
}