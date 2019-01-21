package com.am

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface RecentCallDao {

    @Query("SELECT * from recentcall")
    fun getAll(): LiveData<List<RecentCall>>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: RecentCall)

    @Query("DELETE from recentcall")
    fun deleteAll()



}