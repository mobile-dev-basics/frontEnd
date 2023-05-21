package com.example.todoapp.repository

import com.example.todoapp.dto.ToDoSendDTO
import com.example.todoapp.service.MainApiService
import com.example.todoapp.service.apiRequestFlow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService
) {

    fun getAll() = apiRequestFlow {
        mainApiService.getAllTodo()
    }

    fun getOne(todoId : Long) = apiRequestFlow {
        mainApiService.getOneTodo(todoId)
    }

    fun addOne(todo: ToDoSendDTO) = apiRequestFlow {
        mainApiService.addTodo(todo)
    }

    fun deleteOne(todoId: Long) = apiRequestFlow {
        mainApiService.deleteTodoById(todoId)
    }

    fun updateOne(todoId: Long, todo: ToDoSendDTO) = apiRequestFlow {
        mainApiService.updateTodoById(todoId, todo)
    }
}