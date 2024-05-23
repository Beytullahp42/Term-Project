package tr.igb.todoapp

import android.app.Application
import tr.igb.todoapp.data.Graph

class ToDoApp: Application(){
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}