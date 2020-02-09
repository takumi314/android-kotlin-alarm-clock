package com.example.myalarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

// アラームマネージャからの通知を受け取るクラス
class AlarmBroadcastReceiver : BroadcastReceiver() {

    // ブロードキャストインデントを受け取った時にコールされる
    // ^ intent: 受け取ったインテントの情報を取得することができる
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        TODO("AlarmBroadcastReceiver.onReceive() is not implemented")
    }
}
