package com.am.dialerstates

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.os.IBinder
import android.telecom.Call
import android.telecom.CallAudioState
import android.telecom.InCallService
import android.util.Log
import com.am.InCallActivity


class CallService : InCallService() {
    public var incoming: Boolean = false

    companion object {
        private const val LOG_TAG = "CallService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("CallService", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {

        super.onCreate()
        Log.d("CallService", "oncreate")
    }

    override fun onCallAudioStateChanged(audioState: CallAudioState?) {
        Log.d("CallService", "onCallAudioStateChanged")
        super.onCallAudioStateChanged(audioState)
    }

    override fun onConnectionEvent(call: Call?, event: String?, extras: Bundle?) {
        Log.d("CallService", "onConnectionEvent")
        super.onConnectionEvent(call, event, extras)
    }

    override fun onBringToForeground(showDialpad: Boolean) {
        Log.d("CallService", "onBringToForeground")
        super.onBringToForeground(showDialpad)
    }

    override fun onCanAddCallChanged(canAddCall: Boolean) {
        Log.d("CallService", "onCanAddCallChanged")
        super.onCanAddCallChanged(canAddCall)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("CallService", "onBInd")
        return super.onBind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("CallService", "onUnBind")
        return super.onUnbind(intent)
    }

    override fun onSilenceRinger() {
        Log.d("CallService", "onSilenceRinger")
        super.onSilenceRinger()
    }

    override fun onCallAdded(call: Call) {
        Log.d("CallService", "oncalladded")
        //   Log.d("CallService", call?.details)

        call.registerCallback(callCallback)

        startActivity(Intent(this, InCallActivity::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK))
        CallManager.updateCall(call)


    }

    override fun onCallRemoved(call: Call) {
        //UpdateEnd(this.application, incoming).execute(call)
        call.unregisterCallback(callCallback)
        CallManager.updateCall(null)
    }

    private val callCallback = object : Call.Callback() {
        override fun onStateChanged(call: Call, state: Int) {

            var logstr = when (call.state) {
                0 -> "STATE_NEW"
                1 -> "STATE_DIALING"
                2 -> "STATE_RINGING"
                3 -> "STATE_HOLDING"
                4 -> "STATE_ACTIVE"
                7 -> "STATE_DISCONNECTED"
                8 -> "STATE_SELECT_PHONE_ACCOUNT"
                9 -> "STATE_CONNECTING"
                10 -> "STATE_DISCONNECTING"
                11 -> "STATE_PULLING_CALL"
                else -> "UNKNOWN"
            }

            Log.d("CallState", logstr)




            CallManager.updateCall(call)

        }
    }


}