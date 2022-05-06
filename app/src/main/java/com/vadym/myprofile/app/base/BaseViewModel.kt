package com.vadym.myprofile.app.base

import androidx.lifecycle.*
import com.vadym.myprofile.app.utils.NetworkErrorParser
import com.vadym.myprofile.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject protected lateinit var errorParser: NetworkErrorParser
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

    suspend fun <T> onResult(result: Result<T, Throwable>): T?{
        return when (result) {
            is Result.Success -> result.data
            is Result.Failure -> {
                eventChannel.send(errorParser.parseErrorMessage(result.errorCode))
                result.data
            }
        }
    }

    suspend fun <T> onResult(result: Result<T, Throwable>, liveData: MutableLiveData<T>) {
        when (result) {
            is Result.Success -> liveData.value = result.data
            is Result.Failure -> {
                if (result.data != null) {
                    liveData.value = result.data
                }
                eventChannel.send(errorParser.parseErrorMessage(result.errorCode))
            }
        }
    }

    fun <T> onFlowResult(result: Result<Flow<T>, Throwable>): LiveData<T> {
        return liveData {
            when (result) {
                is Result.Success -> {
                    emitSource(result.data.asLiveData())
                }
                is Result.Failure -> {
                    result.data?.let {
                        emitSource(result.data!!.asLiveData())
                    }
                    eventChannel.send(errorParser.parseErrorMessage(result.errorCode))
                }
            }
        }
    }
}