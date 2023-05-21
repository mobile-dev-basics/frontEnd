package com.example.todoapp.dto

import java.time.LocalDate

data class TodoModelDTO(
    val id : Long,
    val title : String,
    val description: String,
    val creationDate: LocalDate,
    val endDate: LocalDate,
    val priority: String
)
