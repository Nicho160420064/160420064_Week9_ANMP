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

class ListTodoViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = util().buildDb(getApplication())
            //val db = Room.databaseBuilder(
                //getApplication(),
                //TodoDatabase::class.java, "newtododb").build()
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
    fun update_isdone(uuid:Int) {
        launch {
            val db = util().buildDb(getApplication())
            //val db = Room.databaseBuilder(
                //getApplication(),
                //TodoDatabase::class.java, "newtododb").build()
            db.todoDao().updateIs_donw(uuid)
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
}
