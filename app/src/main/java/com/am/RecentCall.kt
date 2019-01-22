package com.am

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RecentCall(
    @PrimaryKey(autoGenerate = true)
    var c_id: Int? = null,
    var time_started: String? = null,
    var incoming: Boolean? = null,
    var time_ended: String? = null,
    var number: String? = null

)