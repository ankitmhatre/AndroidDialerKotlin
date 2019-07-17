package com.am.dialerstates

data class GsmCall(val status: GsmCall.Status, val displayName: String?) {

    enum class Status {
        CONNECTING,//a
        DIALING,
        RINGING,
        ACTIVE,
        DISCONNECTED,
        UNKNOWN
    }
}