package com.example.threadcommunication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.threadcommunication.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.activity_main
}