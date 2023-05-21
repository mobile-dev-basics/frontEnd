package com.example.todoapp.model

import androidx.lifecycle.MutableLiveData
import com.example.todoapp.dto.ToDoSendDTO
import com.example.todoapp.dto.TodoModelDTO
import com.example.todoapp.repository.MainRepository
import com.example.todoapp.service.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel()
{

    private val _todoList = MutableLiveData<ApiResponse<MutableList<TodoModel>>>()
    private val _oneTodo = MutableLiveData<ApiResponse<TodoModel>>()
    private val _afterAdding = MutableLiveData<ApiResponse<TodoModel>>()
    private val _result = MutableLiveData<ApiResponse<Boolean>>()

    val todoList = _todoList
    val oneTodo = _oneTodo
    val afterAdding = _afterAdding
    val result = _result

    fun getAll(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _todoList,
        coroutinesErrorHandler
    ){
        mainRepository.getAll()
    }

    fun getOne(todoId: Long, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _oneTodo,
        coroutinesErrorHandler
    ){
        mainRepository.getOne(todoId)
    }

    fun addOne(todo: ToDoSendDTO, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _afterAdding,
        coroutinesErrorHandler
    ){
        mainRepository.addOne(todo)
    }

    fun deleteOne(todoId: Long, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _result,
        coroutinesErrorHandler
    ){
        mainRepository.deleteOne(todoId)
    }

    fun updateOne(todoId: Long, todo: ToDoSendDTO, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _result,
        coroutinesErrorHandler
    ){
        mainRepository.updateOne(todoId, todo)
    }
}