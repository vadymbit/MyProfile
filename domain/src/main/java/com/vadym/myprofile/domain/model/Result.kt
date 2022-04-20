package com.vadym.myprofile.domain.model

sealed class Result<out S, out F> {

    data class Success<S>(val data: S) : Result<S, Nothing>()

    data class Failure<D>(val data: D?, val errorCode: Int) : Result<D, Nothing>()

}
