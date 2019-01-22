package com.am

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UssdItem(
    @PrimaryKey(autoGenerate = true)
    var ussd_id: String? = null,
    var ussd_no: String? = null,
    var done: Boolean? = null,
    var timeAtCreation: String? = null,
    var minutes: String? = null
)