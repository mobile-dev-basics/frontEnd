package com.example.todoapp.service

import com.example.todoapp.dto.ToDoSendDTO
import com.example.todoapp.dto.TodoModelDTO
import com.example.todoapp.model.TodoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApiService {
    @POST("/api/todo")
    suspend fun addTodo(
        @Body todo : ToDoSendDTO
    ) : Response<TodoModel>

    @GET("/api/todo")
    suspend fun getAllTodo(
    ) : Response<MutableList<TodoModel>>

    @GET("/api/todo/{todoId}")
    suspend fun getOneTodo(
        @Path("todoId")  todoId : Long
    ) : Response<TodoModel>

    @DELETE("/api/todo/{todoId}")
    suspend fun deleteTodoById(
        @Path("todoId") todoId: Long
    ) : Response<Boolean>

    @PUT("/api/todo/{todoId}")
    suspend fun updateTodoById(
        @Path("todoId") todoId: Long,
        @Body todo: ToDoSendDTO
    ) : Response<Boolean>
}