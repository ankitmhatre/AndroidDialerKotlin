package com.am.dialer

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.am.dialerstates.CallManager
import com.am.dialerstates.GsmCall
import com.am.service.MyConnectionService
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.dialer_number_layout.*
import java.util.regex.Pattern


class Dialer : AppCompatActivity(), View.OnTouchListener, View.OnClickListener {

    private val LOG_TAG = "DialerActivity"
    lateinit var time: String


    lateinit var tel: TelecomManager

    private var updatesDisposable = Disposables.empty()
    private var timerDisposable = Disposables.empty()

    lateinit var menut: Menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer)



        fab.setOnClickListener { view ->
            if (dialerView.visibility == View.GONE) {
                dialerView.visibility = View.VISIBLE
                fab.setImageResource(R.drawable.ic_call)
            } else {

                if (edtInput.length() > 0) {
                    callTheEnteredNumber()
                }
            }

            Log.d("MyInCallService", "inFab")
        }

        edtInput.setOnTouchListener(this)

        if (getSystemService(TelecomManager::class.java).defaultDialerPackage != packageName) {
            Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
                .let(::startActivity)
        }


    }


    override fun onResume() {

        updatesDisposable = CallManager.updates()
            .doOnEach { Log.i(LOG_TAG, "updated call: $it") }
            .doOnError { throwable -> Log.e(LOG_TAG, "Error processing call", throwable) }
            .subscribe { updateView(it) }



        super.onResume()
    }

    fun updateView(gsmCall: GsmCall) {
        if (supportActionBar != null) {
            when (gsmCall.status) {
                GsmCall.Status.DISCONNECTED -> {
                    //red
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#375f91")))

                }
                GsmCall.Status.DIALING -> {
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#5f6368")))
                }
                GsmCall.Status.RINGING -> {
                    //down green
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3d8c39")))
                }
                GsmCall.Status.ACTIVE -> {
                    //indigo
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#5c00d2")))

                }
                GsmCall.Status.UNKNOWN -> {
                    //grey
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#375f91")))

                }
                GsmCall.Status.CONNECTING -> {
                    //grey and blue
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#375f91")))

                }
                //
            }


        }
        Log.d("DialerActivity", gsmCall.status.toString())
    }

    fun createPhoneAccountHandle(context: Context, accountName: String): PhoneAccountHandle {
        return PhoneAccountHandle(
            ComponentName(context, MyConnectionService::class.java),
            accountName
        )
    }

    private fun callTheEnteredNumber() {
        callThisPerson()
    }


    fun buttonClickEvent(v: View) {
        when (v.id) {
            R.id.btnOne -> {
                edtInput.append("1")
            }
            R.id.btnTwo -> {
                edtInput.append("2")
            }
            R.id.btnThree -> {
                edtInput.append("3")
            }
            R.id.btnFour -> {
                edtInput.append("4")
            }
            R.id.btnFive -> {
                edtInput.append("5")
            }
            R.id.btnSix -> {
                edtInput.append("6")
            }
            R.id.btnSeven -> {
                edtInput.append("7")
            }
            R.id.btnEight -> {
                edtInput.append("8")
            }
            R.id.btnNine -> {
                edtInput.append("9")
            }
            R.id.btnZero -> {
                edtInput.append("0")
            }
            R.id.btnAterisk -> {
                edtInput.append("*")
            }
            R.id.btnHash -> {
                edtInput.append("#")
            }

        }
    }


    fun callThisPerson() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    private fun validateUSSD(str: String): Boolean {
        val sPattern = Pattern.compile("^\\*[0-9\\*#]*[0-9]+[0-9\\*#]*#$")

        var value = sPattern.matcher(str).matches()

        return value
    }

    private fun callPhone() {
        var handller = Handler()
        handller.postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            Log.d("ussddata", "inside handler")
            // finish()
        }, 3000)

        //  var phonecall = createPhoneAccountHandle(this, MyConnectionService.EXTRA_PHONE_ACCOUNT)
        Log.d("MyInCallService", "callPhone start")

        var s = edtInput.text.toString()
        var callstring: String


        var uri: Uri = Uri.fromParts("tel", edtInput.text.toString(), null)
        var bundle: Bundle = Bundle()
        //Connection.PROPERTY_SELF_MANAGED
        /* bundle.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, true)
     bundle.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phonecall)*/

        Log.d("MyInCallService", "callPhone after 3 lines ")

        tel = getSystemService(Context.TELECOM_SERVICE) as TelecomManager


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            tel.placeCall(uri, bundle)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            909 -> {
                Log.d("ussddata", data.toString())
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val inType = edtInput.inputType // backup the input type
        edtInput.inputType = InputType.TYPE_NULL // disable soft input
        edtInput.onTouchEvent(event) // call native handler
        edtInput.inputType = inType


        var DRAWABLE_LEFT = 0
        var DRAWABLE_TOP = 1
        var DRAWABLE_RIGHT = 2
        var DRAWABLE_BOTTOM = 3

        if (event != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (edtInput.getRight() - edtInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (edtInput.length() > 0) {
                        edtInput.setText(edtInput.text.substring(0, edtInput.text.toString().length - 1))
                        edtInput.setSelection(edtInput.getText().length)//position cursor at the end of the line
                    }
                    return true
                }
            }
        }



        return true
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.call_list_rv -> {
                Log.d("call_lis_rv", "clicked")
                dialerView.visibility = View.GONE
            }
        }
    }


}
