package com.vadym.myprofile.app.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

abstract class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()

    protected fun launch(
        supervisor: Boolean = true,
        block: suspend CoroutineScope.() -> Unit
    ) : Job {
        return viewModelScope.launch {
            if (supervisor) {
                supervisorScope {
                    block.invoke(this)
                }
            } else {
                block.invoke(this)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Debuging", "${this.javaClass.simpleName}  Destroyed")
    }
}