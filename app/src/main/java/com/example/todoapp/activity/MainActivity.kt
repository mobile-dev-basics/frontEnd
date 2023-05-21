package com.example.todoapp.activity

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.TodoModel
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.model.CoroutinesErrorHandler
import com.example.todoapp.model.MainViewModel
import com.example.todoapp.service.ApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private var list = arrayListOf<TodoModel>()
    var adapter = TodoAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.todoRv.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        displayTodo()
        viewModel.todoList.observe(this){
            when(it){
                is ApiResponse.Failure ->
                    binding.loginTV.text = "it.errorMessage"
                is ApiResponse.Loading ->
                    binding.loginTV.text = "GetAllIsLoading"
                is ApiResponse.Success -> {
                    list.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.oneTodo.observe(this){

            when(it){
                is ApiResponse.Failure ->
                    binding.loginTV.text = it.errorMessage
                is ApiResponse.Loading ->
                    binding.loginTV.text = "GetOneIsLoading"
                is ApiResponse.Success -> {
                    val todo = TodoModel(
                        it.data.id,
                        it.data.title,
                        it.data.description,
                        it.data.priority,
                        it.data.creationDate,
                        it.data.endDate
                    )
                    list.add(todo)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.result.observe(this){
            when(it){
                is ApiResponse.Failure ->
                    binding.loginTV.text = it.errorMessage
                is ApiResponse.Loading ->
                    binding.loginTV.text = "PUT/DELETE"
                is ApiResponse.Success -> {
                    adapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.afterAdding.observe(this){
            when(it){
                is ApiResponse.Failure ->
                    binding.loginTV.text = it.errorMessage
                is ApiResponse.Loading ->
                    binding.loginTV.text = "Loading"
                is ApiResponse.Success -> {
                    adapter.notifyDataSetChanged()
                }
            }
        }
        binding.todoRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        initSwipe()
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {

                    }
                } else if (direction == ItemTouchHelper.RIGHT) {
                    GlobalScope.launch(Dispatchers.IO) {
                    }
                }
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    val paint = Paint()
                    val icon: Bitmap

                    if (dX > 0) {
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_check_white_png)
                        paint.color = Color.parseColor("#388E3C")
                        canvas.drawRect(
                            itemView.left.toFloat(), itemView.top.toFloat(),
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat(), paint
                        )
                        canvas.drawBitmap(
                            icon,
                            itemView.left.toFloat(),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )

                    } else {
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_delete_white_png)
                        paint.color = Color.parseColor("#D32F2F")
                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )
                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX

                } else {
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }


        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.todoRv)
    }

    private fun displayTodo() {
        viewModel.getAll(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
                binding.toolbar.title = "Error $message"
            }
        })
    }

    fun openNewTask(view: View) {
        startActivity(Intent(this, TaskActivity::class.java))
    }
}
