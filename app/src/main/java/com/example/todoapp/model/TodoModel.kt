package com.example.todoapp.model

import java.time.LocalDate


// we are making list for each task

data class TodoModel(
    var id:Long ,
    var title:String,
    var description:String,
    var priority: String,
    var creationDate: LocalDate,
    var endDate: Long
)
