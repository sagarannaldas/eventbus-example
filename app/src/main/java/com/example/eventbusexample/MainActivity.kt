package com.example.eventbusexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.eventbusexample.events.MessageEvent
import com.example.eventbusexample.events.MessageEvent2
import com.example.eventbusexample.services.MyIntentService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var txt_output: TextView
    private lateinit var btn_runcode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        txt_output = findViewById(R.id.txt_output)
        btn_runcode = findViewById(R.id.btn_runcode)

        EventBus.getDefault().register(this)

        btn_runcode.setOnClickListener {
            val serviceIntent = Intent(this, MyIntentService::class.java)
            serviceIntent.putExtra("key1", "This is message 1")
            serviceIntent.putExtra("key2", "This is message 2")

            startService(serviceIntent)

            Log.d(TAG, "onCreate: $serviceIntent")
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public fun onEventReceived(event: MessageEvent) {

        Toast.makeText(this, "Message event 1 called", Toast.LENGTH_SHORT).show()

        if (txt_output.text.equals("Ready to Learn!"))
            txt_output.text = ""

        txt_output.append("${event.message} \n")
        txt_output.append(Thread.currentThread().name+"\n")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEventReceived(event: MessageEvent2) {

        Toast.makeText(this, "Message event 2 called", Toast.LENGTH_SHORT).show()


        if (txt_output.text.equals("Ready to Learn!"))
            txt_output.text = ""

        txt_output.append("${event.message} \n")
        txt_output.append(Thread.currentThread().name+"\n")

    }
 
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}