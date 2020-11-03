package com.example.threadcommunication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.threadcommunication.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout.pipeExample.setOnClickListener {
            startActivity(Intent(this, PipeExampleActivity::class.java))
        }

        layout.looperExample.setOnClickListener {
            startActivity(Intent(this, LooperActivity::class.java))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_main
}