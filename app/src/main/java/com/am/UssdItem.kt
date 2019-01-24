package com.am

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UssdItem(
    @PrimaryKey(autoGenerate = true)
    var ussd_id: Int? = null,
    var ussd_no: String? = null,
    var credits: String? = null,
    var timeofTransaction: Long? = null,
    var statusofthistransaction: String? = null,
    var done: Boolean? = null


)