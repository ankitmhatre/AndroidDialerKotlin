package com.am

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.am.arelok.CallManager
import com.am.arelok.GsmCall
import com.am.dialer.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_call.*
import java.util.concurrent.TimeUnit


class InCallActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "CallActivity"
    }

    private var updatesDisposable = Disposables.empty()
    private var timerDisposable = Disposables.empty()
    var params = RelativeLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT as Int,
        ViewGroup.LayoutParams.WRAP_CONTENT as Int
    )
    private var SENSOR_SENSITIVITY = 4
    lateinit var mSensorManager: SensorManager
    lateinit var mProximity: Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        hideBottomNavigationBar()

        buttonHangup.setOnClickListener { CallManager.cancelCall() }
        buttonAnswer.setOnClickListener { CallManager.acceptCall() }

        params.addRule(RelativeLayout.CENTER_HORIZONTAL)

        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)


    }

    /* override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

     }

     override fun onSensorChanged(event: SensorEvent) {
         if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
             //near


             Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show()
         } else {
             window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

             //far
             Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
         }

     }
 */
    override fun onResume() {

        updatesDisposable = CallManager.updates()
            .doOnEach { Log.i(LOG_TAG, "updated call: $it") }
            .doOnError { throwable -> Log.e(LOG_TAG, "Error processing call", throwable) }
            .subscribe { updateView(it) }


        // mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }

    private fun updateView(gsmCall: GsmCall) {
        textStatus.visibility = when (gsmCall.status) {
            GsmCall.Status.ACTIVE -> View.GONE
            else -> View.VISIBLE
        }



        textStatus.text = when (gsmCall.status) {
            GsmCall.Status.CONNECTING -> "Connecting…"
            GsmCall.Status.DIALING -> "Calling…"
            GsmCall.Status.RINGING -> "Incoming call"
            GsmCall.Status.ACTIVE -> {

                "In Call"
            }
            GsmCall.Status.DISCONNECTED -> "Finished call"
            GsmCall.Status.UNKNOWN -> ""
        }
        textDuration.visibility = when (gsmCall.status) {
            GsmCall.Status.ACTIVE -> View.VISIBLE
            else -> View.GONE
        }
        buttonHangup.visibility = when (gsmCall.status) {
            GsmCall.Status.DISCONNECTED -> View.GONE
            else -> View.VISIBLE
        }

        if (gsmCall.status == GsmCall.Status.DISCONNECTED) {
            buttonHangup.postDelayed({ finish() }, 3000)
        }

        when (gsmCall.status) {
            GsmCall.Status.ACTIVE -> startTimer()
            GsmCall.Status.DISCONNECTED -> stopTimer()
            else -> Unit
        }

        textDisplayName.text = gsmCall.displayName ?: "Unknown"

        buttonAnswer.visibility = when (gsmCall.status) {
            GsmCall.Status.RINGING -> View.VISIBLE
            else -> View.GONE
        }
    }

    override fun onStart() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        updatesDisposable.dispose()
        //  mSensorManager.unregisterListener(this)
    }


    private fun hideBottomNavigationBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun startTimer() {
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                textDuration.text = it.toDurationString()

            }
    }

    private fun stopTimer() {


        timerDisposable.dispose()
    }

    private fun Long.toDurationString() = String.format("%02d:%02d:%02d", this / 3600, (this % 3600) / 60, (this % 60))


}
