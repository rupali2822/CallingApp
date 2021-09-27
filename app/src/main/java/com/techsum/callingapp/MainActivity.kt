package com.techsum.callingapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sms.setOnClickListener{
            startActivity(Intent(this,SmsActivity::class.java))
        }


        btn_call.setOnClickListener{
            //step 1: get the runtime permission status
            var status=ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)

            //you can make phone call only if user is allowed other wise request again
            if(status== PackageManager.PERMISSION_GRANTED){
              makeCall()
          }else{
              ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),15)
            }
        }


    }
private fun makeCall(){
    var mobileno=et_mobile_number.text.toString()
    var intent=Intent()
    intent.action=Intent.ACTION_CALL
    intent.setData(Uri.parse("tel:{$mobileno}"))
    startActivity(intent)
}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            makeCall()
        }else {

            Toast.makeText(this, "User is not allowed here", Toast.LENGTH_SHORT).show()
        }
    }


}