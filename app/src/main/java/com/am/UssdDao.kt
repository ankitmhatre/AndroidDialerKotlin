package com.am

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UssdDao {
    @Query("SELECT * from ussditem WHERE done = 'true' ORDER BY timeofTransaction DESC")
    fun getAll(): LiveData<List<UssdItem>>

    @Insert(onConflict = REPLACE)
    fun insert(ussdItem: UssdItem)

    @Query("DELETE FROM ussditem WHERE ussd_id = :cid ")
    fun delete(cid: String)

    @Query("DELETE from ussditem")
    fun deleteAll()

    @Query("UPDATE ussditem SET done = :done ,timeofTransaction=:timeoftransition , statusofthistransaction= :transactionStatus")
    fun updateThis(

        done: Boolean,
        timeoftransition: String,
        transactionStatus: String
    )

}