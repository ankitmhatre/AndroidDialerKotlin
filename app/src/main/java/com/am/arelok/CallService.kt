package com.am.arelok

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.AsyncTask
import android.os.Bundle
import android.os.IBinder
import android.telecom.Call
import android.telecom.CallAudioState
import android.telecom.InCallService
import android.util.Log
import com.am.InCallActivity
import com.am.RecentCall
import com.am.RecentCallDao
import com.am.RecentCallDatabase


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
        UpdateEnd(this.application, incoming).execute(call)
        call.unregisterCallback(callCallback)
        CallManager.updateCall(null)
    }

    private val callCallback = object : Call.Callback() {
        override fun onStateChanged(call: Call, state: Int) {
            Log.d("stateofthecall", call.state.toString())
            when (call.state) {
                1 -> {
                    incoming = false
                    InsertRecenCall(this@CallService.application, incoming).execute(call)
                }
                2 -> {
                    incoming = true
                    InsertRecenCall(application, incoming).execute(call)
                }
            }

            CallManager.updateCall(call)

        }
    }

    class InsertRecenCall : AsyncTask<Call, Void, String> {
        var application: Application
        var recentCallDao: RecentCallDao
        var incoming: Boolean

        constructor(application: Application, incoming: Boolean) {
            this.application = application
            recentCallDao = RecentCallDatabase.getInstance(application).RecentCallDao()
            this.incoming = incoming

        }


        override fun doInBackground(vararg params: Call?): String {
            var newno = 1 + recentCallDao.getList().last()
            var rcall = RecentCall(
                c_id = null,
                time_started = System.currentTimeMillis().toString(),
                incoming = incoming,
                time_ended = null,
                number = params[0]?.details?.handle?.schemeSpecificPart
            )
            recentCallDao.insert(
                rcall
            )

            Log.d("recentcall", rcall.toString())

            return ""
        }
    }

    class UpdateEnd : AsyncTask<Call, Void, String> {
        lateinit var application: Application
        lateinit var recentCallDao: RecentCallDao
        var incoming: Boolean

        constructor(application: Application, incoming: Boolean) {
            this.application = application
            recentCallDao = RecentCallDatabase.getInstance(application).RecentCallDao()
            this.incoming = incoming

        }

        override fun doInBackground(vararg params: Call?): String {
            var newno = 1 + recentCallDao.getList().last()
            var updatCall = RecentCall(
                c_id = (recentCallDao.getList().last()),
                incoming = incoming,
                time_started = null,

                time_ended = System.currentTimeMillis().toString(),
                number = params[0]?.details?.handle?.schemeSpecificPart
            )
            recentCallDao.update(
                updatCall
            )
            Log.d("recentcall", updatCall.toString())

            return ""
        }

    }


}