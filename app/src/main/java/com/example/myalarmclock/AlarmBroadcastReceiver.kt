package com.example.myalarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// アラームマネージャからの通知を受け取るクラス
class AlarmBroadcastReceiver : BroadcastReceiver() {

    // ブロードキャストインデントを受け取った時にコールされる
    // ^ intent: 受け取ったインテントの情報を取得することができる
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context , "アラームを受診しました。", Toast.LENGTH_SHORT)    // トーストを作成する
            .show() // トーストを表示する
    }

}
