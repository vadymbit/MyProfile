package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.Const.FACEBOOK
import com.vadym.myprofile.data.Const.INSTAGRAM
import com.vadym.myprofile.data.Const.LINKEDIN
import com.vadym.myprofile.data.Const.PROFILE_ADDRESS
import com.vadym.myprofile.data.Const.PROFILE_BIRTHDAY
import com.vadym.myprofile.data.Const.PROFILE_CAREER
import com.vadym.myprofile.data.Const.PROFILE_NAME
import com.vadym.myprofile.data.Const.PROFILE_PHONE
import com.vadym.myprofile.data.Const.PROFILE_PHOTO
import com.vadym.myprofile.data.Const.TWITTER
import com.vadym.myprofile.data.model.ApiResult
import com.vadym.myprofile.data.model.UserDTO
import com.vadym.myprofile.data.model.mapper.UserDTOMapper
import com.vadym.myprofile.data.model.mapper.UserDomainMapper
import com.vadym.myprofile.data.source.local.AuthLocalStorage
import com.vadym.myprofile.data.source.local.ProfileLocalStorage
import com.vadym.myprofile.data.source.remote.ApiService
import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val storage: ProfileLocalStorage,
    private val authStorage: AuthLocalStorage,
    private val apiService: ApiService
) : ProfileRepository, BaseRepository() {

    override suspend fun getUserProfile(): Result<Flow<UserModel>, Throwable> {
        val apiResult = getRequestResult { apiService.getUserProfile() }
        return getFlowResult(
            apiResult = apiResult,
            saveNewData = { saveProfileToCache((apiResult as ApiResult.Success).data.user) },
            getCachedData = {
                storage.getMyProfile(authStorage.getProfileId()).filterNotNull()
                    .map { UserDomainMapper.toUserModel(it) }
            }
        )
    }

    override fun getProfileId(): Int {
        return authStorage.getProfileId()
    }

    override suspend fun editUserProfile(userModel: UserModel): Result<Boolean, Throwable> {
        val multipartBody = buildMultipartUserBody(userModel)
        val response = getRequestResult { apiService.editUser(multipartBody) }
        return getResult(response) {
            storage.insertUserProfile(
                UserDTOMapper.toUserDB((response as ApiResult.Success).data.user)
            )
        }
    }

    private fun buildMultipartUserBody(userModel: UserModel): MultipartBody {
        val image = File(userModel.urlPhoto)
        val multipartUser = MultipartBody.Builder().setType(MultipartBody.FORM)
        addFieldIfNotBlank(PROFILE_NAME, userModel.name, multipartUser)
        addFieldIfNotBlank(PROFILE_PHONE, userModel.phoneNumber, multipartUser)
        addFieldIfNotBlank(PROFILE_CAREER, userModel.career, multipartUser)
        addFieldIfNotBlank(PROFILE_ADDRESS, userModel.address, multipartUser)
        addFieldIfNotBlank(PROFILE_BIRTHDAY, userModel.birthDate, multipartUser)
        addFieldIfNotBlank(FACEBOOK, userModel.facebook, multipartUser)
        addFieldIfNotBlank(INSTAGRAM, userModel.instagram, multipartUser)
        addFieldIfNotBlank(TWITTER, userModel.twitter, multipartUser)
        addFieldIfNotBlank(LINKEDIN, userModel.linkedin, multipartUser)
        if (image.exists()) {
            multipartUser.addFormDataPart(
                PROFILE_PHOTO,
                image.name,
                RequestBody.create(MediaType.parse("image/*"), image)
            )
        }
        return multipartUser.build()
    }

    private fun addFieldIfNotBlank(
        field: String,
        fieldValue: String,
        multipartUser: MultipartBody.Builder
    ) {
        if (fieldValue.isNotBlank()) {
            multipartUser.addFormDataPart(field, fieldValue)
        }
    }

    private suspend fun saveProfileToCache(profile: UserDTO) {
        storage.insertUserProfile(UserDTOMapper.toUserDB(profile))
    }
}