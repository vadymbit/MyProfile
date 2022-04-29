package com.vadym.myprofile.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vadym.myprofile.data.model.UserDB
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContacts(userList: List<UserDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(userDB: UserDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: UserDB)

    @Query("SELECT * FROM users WHERE name LIKE '%' || :contactName || '%'")
    fun searchContactsByName(contactName: String): Flow<List<UserDB>>

    @Query("SELECT * FROM users WHERE uid != :profileId ORDER BY name")
    fun getAllUserContact(profileId: Int): Flow<List<UserDB>>

    @Query("SELECT * FROM users WHERE uid = :profileId")
    fun getProfile(profileId: Int): Flow<UserDB>

    @Query("DELETE FROM users WHERE uid = :id ")
    fun deleteContact(id: Int): Int

    @Query("DELETE FROM users")
    fun deleteAll(): Int

}