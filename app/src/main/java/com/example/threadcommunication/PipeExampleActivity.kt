package com.example.threadcommunication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.threadcommunication.databinding.ActivityPipeExampleBinding
import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter

class PipeExampleActivity : BaseActivity<ActivityPipeExampleBinding>() {
    private lateinit var reader: PipedReader
    private lateinit var writer: PipedWriter
    private lateinit var workThread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reader = PipedReader()
        writer = PipedWriter()

        try {
            writer.connect(reader)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        layout.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    // only handle count increase condition

                    Log.d(TAG, "start: $start, count: $count, before: $before")

                    if (count > before) {
                        s?.subSequence(before, count)?.toString()?.let {
                            writer.write(it)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })

        workThread = Thread(TextHandlerTask(reader))
        workThread.start()
    }

    override fun getLayoutId(): Int = R.layout.activity_pipe_example

    override fun onDestroy() {
        super.onDestroy()
        workThread.interrupt()

        try {
            writer.close()
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    class TextHandlerTask(private val reader: PipedReader) : Runnable {
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    var i: Int
                    while (reader.read().also { i = it } != -1) {
                        val c = i.toChar()
                        Log.d(TAG, "char: $c")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

    companion object {
        const val TAG = "PipeExampleActivity"
    }
}