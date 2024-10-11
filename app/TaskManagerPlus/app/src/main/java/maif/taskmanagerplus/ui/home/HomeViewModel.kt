package maif.taskmanagerplus.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to Task Manager Plus, your go-to app for organizing tasks efficiently. Create, edit, and manage your tasks with ease, filter by Pending or Completed status, and stay on top of your work!"
    }
    val text: LiveData<String> = _text
}