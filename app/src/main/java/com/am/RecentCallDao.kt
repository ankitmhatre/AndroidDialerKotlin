package com.am

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update


@Dao
interface RecentCallDao {

    @Query("SELECT * from recentcall ORDER BY time_started DESC")
    fun getAll(): LiveData<List<RecentCall>>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: RecentCall)

    @Update(onConflict = REPLACE)
    fun update(weatherData: RecentCall)

    @Query("DELETE FROM recentcall WHERE c_id = :cid ")
    fun delete(cid: String)

    @Query("DELETE from recentcall")
    fun deleteAll()

    @Query("SELECT c_id from recentcall")
    fun getList(): List<Int>


}