package com.vadym.myprofile.app.base

import android.util.Log
import androidx.lifecycle.*
import com.vadym.myprofile.app.utils.ParseNetworkError.parseErrorMessage
import com.vadym.myprofile.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

abstract class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    private val eventChannel = Channel<String>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    protected fun launch(
        supervisor: Boolean = true,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
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

    suspend fun <T> onResult(result: Result<T, Exception>): T?{
        return when (result) {
            is Result.Success -> result.data
            is Result.Failure -> {
                eventChannel.send(parseErrorMessage(result.errorCode))
                result.data
            }
        }
    }

    suspend fun <T> onResult(result: Result<T, Exception>, liveData: MutableLiveData<T>) {
        when (result) {
            is Result.Success -> liveData.value = result.data
            is Result.Failure -> {
                if (result.data != null) {
                    liveData.value = result.data
                }
                eventChannel.send(parseErrorMessage(result.errorCode))
            }
        }
    }

    fun <T> onFlowResult(result: Result<Flow<T>, Exception>): LiveData<T> {
        return liveData {
            when (result) {
                is Result.Success -> {
                    emitSource(result.data.asLiveData())
                }
                is Result.Failure -> {
                    result.data?.let {
                        emitSource(result.data!!.asLiveData())
                    }
                    eventChannel.send(parseErrorMessage(result.errorCode))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Debuging", "${this.javaClass.simpleName}  Destroyed")
    }
}