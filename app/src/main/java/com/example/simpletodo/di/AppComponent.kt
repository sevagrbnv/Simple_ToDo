package com.example.simpletodo.di

import android.app.Application
import com.example.simpletodo.presentation.MainActivity
import com.example.simpletodo.presentation.MainFragment.MainFragment
import com.example.simpletodo.presentation.TodoItemFragment.TodoItemFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: MainFragment)

    fun inject(fragment: TodoItemFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}