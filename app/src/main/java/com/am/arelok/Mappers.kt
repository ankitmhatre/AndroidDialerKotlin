package com.am.arelok

import android.telecom.Call

fun Call.toGsmCall() = GsmCall(
    status = state.toGsmCallStatus(),
    displayName = details.handle.schemeSpecificPart // This will return the phone number as dialed
)

private fun Int.toGsmCallStatus() = when (this) {
    Call.STATE_ACTIVE -> GsmCall.Status.ACTIVE
    Call.STATE_RINGING -> GsmCall.Status.RINGING
    Call.STATE_CONNECTING -> GsmCall.Status.CONNECTING
    Call.STATE_DIALING -> GsmCall.Status.DIALING
    Call.STATE_DISCONNECTED -> GsmCall.Status.DISCONNECTED
    else -> GsmCall.Status.UNKNOWN
}