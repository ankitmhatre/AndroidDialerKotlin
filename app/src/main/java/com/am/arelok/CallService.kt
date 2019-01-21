package com.am.arelok

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


        call.registerCallback(callCallback)

        startActivity(Intent(this, InCallActivity::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK))
        CallManager.updateCall(call)


    }

    override fun onCallRemoved(call: Call) {

        call.unregisterCallback(callCallback)
        CallManager.updateCall(null)
    }

    private val callCallback = object : Call.Callback() {
        override fun onStateChanged(call: Call, state: Int) {
            Log.d("stateofthecall", call.state.toString())
            when(call.state) {
            }
            CallManager.updateCall(call)

        }
    }


}