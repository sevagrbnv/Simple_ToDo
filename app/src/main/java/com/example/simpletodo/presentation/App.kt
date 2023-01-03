package com.example.simpletodo.presentation

import android.app.Application
import com.example.simpletodo.di.DaggerAppComponent

class App: Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}