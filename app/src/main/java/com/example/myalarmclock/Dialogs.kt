package com.example.myalarmclock

import android.content.Context
import androidx.fragment.app.DialogFragment


class TimeAlertDialog : DialogFragment() {

    interface Listener {
        fun getUP()
        fun snooze()
    }

    private var listener: Listener? = null

    // フラグメントが呼ばれ、最初にコンテキストにアタッチされた時にコールされる
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        when (context) {
            // 受け取ったcontextがListenerインターフェイスを持っているかどうかチェックする
            is Listener -> {
                // リスナー用変数にアクティビティをセットする
                listener = context
            }
        }
    }

}
