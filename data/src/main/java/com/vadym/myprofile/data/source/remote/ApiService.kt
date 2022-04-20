package com.vadym.myprofile.data.source.remote

import com.vadym.myprofile.data.model.request.AuthRequest
import com.vadym.myprofile.data.model.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/user/register")
    suspend fun register(@Body authRequest: AuthRequest): Response<ServerResponse<AuthData>>

    @POST("api/user/login")
    suspend fun login(@Body authRequest: AuthRequest): Response<ServerResponse<AuthData>>

    @POST("api/user/profile/edit")
    suspend fun editUser(@Body body: MultipartBody): Response<ServerResponse<UserData>>

    @POST("api/user/auth/refresh")
    suspend fun refreshToken(@Header("RefreshToken") refreshToken: String): Response<ServerResponse<TokenData>>

    @POST("api/user/contact/add")
    @FormUrlEncoded
    suspend fun addContact(@Field("contactId") contactId: Int): Response<ServerResponse<ContactsData>>

    @POST("api/user/contact/delete")
    @FormUrlEncoded
    suspend fun deleteContact(@Field("contactId") contactId: Int): Response<ServerResponse<ContactsData>>

    @GET("api/user/profile")
    suspend fun getUserProfile(): Response<ServerResponse<UserData>>

    @GET("api/users")
    suspend fun getAllUsers(): Response<ServerResponse<GetAllUsersData>>

    @GET("api/user/contacts")
    suspend fun getContacts(): Response<ServerResponse<ContactsData>>
}