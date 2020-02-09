package com.example.myalarmclock

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.time.Year
import java.util.*


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

class DatePickerFragment : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    interface OnDateSelectedListener {
        fun onSelected(year: Int, month: Int, date: Int)
    }

    private var listener: OnDateSelectedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        when (context) {
            is OnDateSelectedListener -> {
                listener = context
            }
        }
    }

    // ダイアログが生成された時、onCreateとOnCreateViewの間にコールされる
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 現在の日付を初期値として設定する
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val date = c.get(Calendar.DAY_OF_MONTH)
        // DatePickerDialogクラスのコンストラクタ
        return DatePickerDialog(requireContext(), this, year, month, date)
    }

    // 日付が選択された時にコールされる
    override fun onDateSet(view: DatePicker?, year: Int,
                           month: Int, dayOfMonth: Int) {
        listener?.onSelected(year, month, dayOfMonth)
    }

}

class TimePickerFragment : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    interface OnTimeSelectedListener {
        fun onSelected(hourOfDay: Int, minute: Int)
    }

    private var listener: OnTimeSelectedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        when (context) {
            is OnTimeSelectedListener -> {
                listener = context
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(context, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener?.onSelected(hourOfDay, minute)
    }

}