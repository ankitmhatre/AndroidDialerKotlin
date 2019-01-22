package com.am

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RecentCall(
    @PrimaryKey(autoGenerate = true)
    var c_id: Int?,
    var time_started: String?,
    var incoming: Boolean,
    var time_ended: String?,
    var number: String?
)