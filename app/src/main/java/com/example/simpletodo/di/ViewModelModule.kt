package com.example.simpletodo.di

import androidx.lifecycle.ViewModel
import com.example.simpletodo.presentation.MainViewModel
import com.example.simpletodo.presentation.TodoItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TodoItemViewModel::class)
    fun bindTodoItemViewModel(viewModel: TodoItemViewModel): ViewModel
}