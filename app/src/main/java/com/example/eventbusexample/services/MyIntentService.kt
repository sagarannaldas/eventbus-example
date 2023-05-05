package com.example.eventbusexample.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.eventbusexample.events.MessageEvent
import com.example.eventbusexample.events.MessageEvent2
import org.greenrobot.eventbus.EventBus

class MyIntentService: IntentService("MyIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val message1 = intent.getStringExtra("key1")
            val message2 = intent.getStringExtra("key2")

            var messageEvent = MessageEvent("$message1 from service")
            Log.d(TAG, "onHandleIntent: message 1 $message1 from service")

            EventBus.getDefault().post(messageEvent)

            Log.d(TAG, "onHandleIntent: message 1 $message1 from service")

            Thread.sleep(3000)

            val messageEvent2 = MessageEvent2("$message2 from service")

            EventBus.getDefault().post(messageEvent2)

        }
    }

    companion object {
        private const val TAG = "MyIntentService"
    }
}