package com.example.messageblocking

import android.Manifest.permission.RECEIVE_SMS
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissions = arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
        )

        val requestCode = 101

        if (ContextCompat.checkSelfPermission(this, permissions[0])
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, permissions[1])
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, permissions[2])
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        }
        setContentView(R.layout.activity_main)
    }
}
class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>
                for (pdu in pdus) {
                    val message = SmsMessage.createFromPdu(pdu as ByteArray)
                    val body = message.messageBody
                    val sender = message.originatingAddress
                    if (sender == "7802996394"){
                        val messageUri = Telephony.Sms.CONTENT_URI
                        val deleteSelection = "${Telephony.Sms.ADDRESS} = ?"
                        val deleteArgs = arrayOf(sender)
                        val testing =
                        context.contentResolver.delete(messageUri, deleteSelection, deleteArgs)
                    }
                }
            }
        }

    }
}