package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.model.ApiResult
import com.vadym.myprofile.data.model.response.ServerResponse
import com.vadym.myprofile.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T> getRequestResult(request: suspend () -> Response<ServerResponse<T>>): ApiResult<T, Throwable> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val response = request()
                if (response.isSuccessful) {
                    response.body()?.let {
                        ApiResult.Success(it.data)
                    } ?: ApiResult.Error(204)
                } else {
                    ApiResult.Error(response.code())
                }
            }
        }.getOrElse {
            ApiResult.Exception(it)
        }
    }

    suspend fun <T, D> getResult(
        apiResult: ApiResult<T, Throwable>,
        onResult: suspend () -> D,
    ): Result<D, Throwable> {
        return when (apiResult) {
            is ApiResult.Exception -> {
                Result.Failure(null, -1)
            }
            is ApiResult.Error -> {
                Result.Failure(null, apiResult.error)
            }
            is ApiResult.Success -> {
                withContext(Dispatchers.IO) {
                    Result.Success(onResult())
                }
            }
        }
    }

    suspend fun <T, D> getFlowResult(
        apiResult: ApiResult<T, Throwable>,
        saveNewData: suspend () -> Unit,
        getCachedData: () -> D
    ): Result<D, Throwable> {
        return when (apiResult) {
            is ApiResult.Exception -> Result.Failure(getCachedData(), -1)
            is ApiResult.Error -> Result.Failure(getCachedData(), apiResult.error)
            is ApiResult.Success -> {
                withContext(Dispatchers.IO) {
                    saveNewData()
                    Result.Success(getCachedData())
                }
            }
        }
    }
}