package com.example.threadcommunication

import android.os.Bundle
import com.example.threadcommunication.databinding.ActivityPipeExampleBinding

class PipeExampleActivity : BaseActivity<ActivityPipeExampleBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pipe_example)
    }

    override fun getLayoutId(): Int = R.layout.activity_pipe_example
}