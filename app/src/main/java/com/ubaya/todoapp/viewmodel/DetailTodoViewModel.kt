package com.ubaya.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.model.TodoDatabase
import com.ubaya.todoapp.util.util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<Todo>()
    private val job = Job()
    fun addTodo(list: List<Todo>) {
        launch {
            val db = util().buildDb(getApplication())
            //val db = Room.databaseBuilder(
                //getApplication(), TodoDatabase::class.java,
                //"newtododb"
            //).build()
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }
    fun fetch(uuid:Int) {
        launch {
            val db = util().buildDb(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }
    fun update(title:String, notes:String, priority:Int, uuid:Int) {
        launch {
            val db = util().buildDb(getApplication())
            db.todoDao().update(title, notes, priority, uuid)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

}