package com.example.dogsandddapters.base

import android.app.Application
import android.content.Context
import android.os.Looper
import androidx.core.os.HandlerCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MyApplication : Application() {
    var executorService: ExecutorService = Executors.newFixedThreadPool(4)

    object Globals{
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        Globals.appContext=applicationContext
    }

    val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val executorService: Any
            get() {
                TODO()
            }
    }

}