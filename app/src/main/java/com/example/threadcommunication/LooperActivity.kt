package com.example.threadcommunication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.example.threadcommunication.databinding.ActivityLooperBinding

class LooperActivity : BaseActivity<ActivityLooperBinding>(), View.OnClickListener {
    private val looperThread = LooperThread()

    override fun getLayoutId(): Int = R.layout.activity_looper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout.triggerButton.setOnClickListener {
            looperThread.handler?.let { // 7. 在背景執行緒上設定 handler 與 UI thread 上的這段 code 存在著 race condition, 因此要驗證 hanlder 是否有效
                val message = it.obtainMessage(0) // 8. 初始化 message object, 特意將 what 變數設為 0
                it.sendMessage(message)
            }
        }
        looperThread.start() // 6. 啟動工作者執行緒，讓它準備好處理訊息
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.trigger_button) {
            Log.d(TAG, "button clicked")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        looperThread.handler?.looper?.quit()  // 10. 終止背景執行緒，looper.quit(), 停止訊息派送，並將 looper.loop()從阻塞中釋放，所以 run 方法可以結束，導致執行緒停止
    }

    class LooperThread: Thread() {  // 1. 工作者執行緒的定義，作為 MessageQueue 的消費者
        var handler : Handler? = null

        override fun run() {
            super.run()
            Looper.prepare() // 2. 將工作者執行緒與 looper關聯起來(隱含地表示與 MessageQueue 關聯在一起)

            // 3. 設定 handler，由於使用預設的 constructor, 因此該 handler 會自動跟當前 thread 的 looper 綁定在一起。
            // 因此，這個 handler 只能在 Looper.prepare() 之後被建立，否則會沒有東西可以連結
            handler = Handler() {
                if (it.what == 0) {  // 4. 當訊息送到工作者執行緒時，callback 會執行
                    doLongRunningOperation()
                }
                true
            }

            Looper.loop() // 5. 開始從 MessageQueue 配送訊息給消費者執行緒。這是阻塞呼叫，所以工作者執行緒不會結束。
        }

        private fun doLongRunningOperation() {
            Log.d(TAG, "doLongRunningOperation")
        }
    }

    companion object {
        const val TAG = "LooperActivity"
    }
}