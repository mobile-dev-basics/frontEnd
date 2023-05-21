package com.example.todoapp.singleton

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class Queue (context: Context) {
    companion object{
        @Volatile
        private var INSTANCE: Queue? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Queue(context).also{
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> add(req: Request<T>){
        requestQueue.add(req)
    }
}