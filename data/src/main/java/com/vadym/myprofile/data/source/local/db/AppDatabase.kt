package com.vadym.myprofile.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vadym.myprofile.data.model.UserDB

@Database(entities = [UserDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}