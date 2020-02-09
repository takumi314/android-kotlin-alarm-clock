package com.example.myalarmclock

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class TimeAlertDialog : DialogFragment() {

    interface Listener {
        fun getUp()
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

    // ダイアログが生成された時、onCreateとOnCreateViewの間にコールされる
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())    // コンストラクタ
        builder.setMessage("時間になりました!")
        builder.setPositiveButton("起きる") { dialog, which ->
            // listenerに保有しているアクティビティのgetUp()メソッドをコールバックする
            listener?.getUp()
        }
        builder.setNegativeButton("あと5分") { dialog, which ->
            // listenerに保有しているアクティビティのsnooze()メソッドをコールバックする
            listener?.snooze()
        }
        return builder.create() // Builderの設定に従いAlertDialogを生成する
    }

}
