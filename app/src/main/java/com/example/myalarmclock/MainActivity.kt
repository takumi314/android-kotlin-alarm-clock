package com.example.myalarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TimeAlertDialog.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // エクストラの"onReceive"に true が指定されて起動されたらダイアログを表示する
        if (intent?.getBooleanExtra("onReceive", false) == true) {
            val dialog = TimeAlertDialog()
            dialog.show(supportFragmentManager, "alert_dialog")
        }

        setContentView(R.layout.activity_main)

        setAlarm.setOnClickListener {
            val calender = Calendar.getInstance()
            /* 現在時刻をセットする */
            calender.timeInMillis = System.currentTimeMillis()
            /* 現在の時刻の5秒後を設定する */
            calender.add(Calendar.SECOND, 5)
            /* AlarmManagerにインスタンスを渡す */
            setAlarmManager(calender)
        }

        cancelAlarm.setOnClickListener {
            cancelAlarmManager()
        }
    }

    // TimeAlertDialog.Listener

    override fun getUp() {
        finish()    // アクティビティを閉じる
    }

    override fun snooze() {
        val calender = Calendar.getInstance()
        calender.timeInMillis = System.currentTimeMillis()
        calender.add(Calendar.MINUTE, 5)    // 5分後にアラームを設定する
        setAlarmManager(calender)
        finish()
    }

    // Private methods

    private fun setAlarmManager(caleneder: Calendar) {
        // AlarmManagerクラスのインスタンスを作成する
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // アラーム時刻になった時にシステムから発行されるインテントを作成する
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        // AlarmManagerに登録するために、作成したインテントを指定してペンディングインテントを登録する
        // - (注) リクエストコードやフラグは今回使用しないため 0 を渡した
        val pending = PendingIntent.getBroadcast(this, 0, intent, 0)

        when {
            // Android 5.0 Lollipop (API 21)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                // AlarmClockInfoクラスのコンストラクタ
                val info = AlarmManager.AlarmClockInfo(caleneder.timeInMillis, null)
                // アラームを設定する
                am.setAlarmClock(info, pending)
            }
            // Android 4.4 KitKat (API 19)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                // アラームが正確に配信されるようにスケジュールする
                am.setExact(AlarmManager.RTC_WAKEUP, caleneder.timeInMillis, pending)
            }
            else -> {
                am.set(AlarmManager.RTC_WAKEUP, caleneder.timeInMillis, pending)
            }

        }
    }

    private fun cancelAlarmManager() {
        // AlarmManagerクラスのインスタンスを作成する
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        val pending = PendingIntent.getBroadcast(this, 0 ,intent, 0)
        // Intentを一致するアラームをすべて削除する
        am.cancel(pending)
    }
}