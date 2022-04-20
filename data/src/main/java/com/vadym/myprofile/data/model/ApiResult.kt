package com.vadym.myprofile.data.model

sealed class ApiResult<out S, out F> {

    data class Success<S>(val data: S) : ApiResult<S, Nothing>()

    data class Error(val error: Int) : ApiResult<Nothing, Nothing>()

    data class Exception<F>(val exception: F) : ApiResult<Nothing, F>()

}