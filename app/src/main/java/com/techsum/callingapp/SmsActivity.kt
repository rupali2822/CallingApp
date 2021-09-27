package com.techsum.callingapp

import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sms.*

class
SmsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        btn_send_sms.setOnClickListener {
            var status = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)

             if(et_mobile_number.text.isEmpty()) {
                Toast.makeText(this, "Please enter complete data.", Toast.LENGTH_SHORT).show()
            }
            else  if (status == PackageManager.PERMISSION_GRANTED ) {

                 sendSms()
             }
              else{

                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),
                            10)

                }
            }
        }



     private fun sendSms() {
        var smsManager = SmsManager.getDefault()

        var cp_code = cp_code.selectedCountryCode.toString()
        var mobile_number = et_mobile_number.text.toString()
        var message = et_message.text.toString()
        var nooftimes = Integer.parseInt(et_number_of_msgs.text.toString())

        var numbers = mobile_number.split(',')

        var full_mobile_number = cp_code + mobile_number

        for (mo_no in numbers) {

            for (x in 1..nooftimes) {

                var si = Intent(this, SendActivity::class.java)
                var send_intent = PendingIntent.getActivity(this, 0, si, 0)


                var di = Intent(this, DeliveredActivity::class.java)
                var deliver_intent = PendingIntent.getActivity(this, 0, di, 0)


                smsManager.sendTextMessage(mo_no, null, message, send_intent, deliver_intent)

            }

        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSms()
        } else {
            Toast.makeText(this, "User is not allowed here", Toast.LENGTH_SHORT).show()
        }
    }


}
