package com.example.todoapp.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.TodoModel
import com.example.todoapp.databinding.ItemTodoBinding
import java.text.SimpleDateFormat
import java.util.*

// first create adapter class. This inherits recycler view. Recycler view now requires view holder
class TodoAdapter(private val list: List<TodoModel>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private lateinit var view : ItemTodoBinding
    // 3 functions of the view holder
    // 1st func
    // In this Layout inflatter is called which converts view in such a form that adapter can consume it
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): TodoViewHolder {
        view = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(
             view
        )
    }


    override fun getItemCount() = list.size
    
    // 2nd func
    // this will set data in each card
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position]) // we are passing the object of the list that we made in the ToDoModel.kt
    }

    // 3rd func
    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    // view holder is present inside the recycler view
    inner class TodoViewHolder(todoView: ItemTodoBinding) : RecyclerView.ViewHolder(todoView.root) {
        fun bind(todoModel: TodoModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.random_color)
                val randomColor = colors[Random().nextInt(colors.size)]
                view.viewColorTag.setBackgroundColor(randomColor)
                view.txtShowTitle.text = todoModel.title
                view.txtShowTask.text = todoModel.description
                view.txtShowCategory.text = todoModel.priority
                updateDate(todoModel.endDate)

            }
        }

        private fun updateDate(time: Long) {
            //Mon, 5 Jan 2020
            val myformat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myformat)
            view.txtShowDate.text = sdf.format(Date(time))

        }
    }

}


