package com.applab.app_abnumber

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.applab.app_abnumber.utils.ABNumber
import java.util.*

class MainActivity : AppCompatActivity() {
    private var ab = ABNumber()
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ab = ABNumber()
        ab.setAns()
    }

    fun onClickSubmitButton(view: View) {
        val guess = "1234"
        val result = ab.getResult(guess)
        Log.d(TAG, Arrays.toString(result))


        Log.d(TAG, Arrays.toString(result))
        //val ans = luckyNumber.ans
        Log.d(TAG, result.toString())
        if (result[0] == 0) {
            // 通知 AlertDialog
            AlertDialog.Builder(this)
                .setTitle(R.string.result)
                .setTitle(R.string.bingo)
                .setMessage(R.string.listener)
                .setPositiveButton(R.string.ok, listener)
                .show()
        }

    }

    //實作一個 AlertDialog 的 onClick 監聽器
    val listener = DialogInterface.OnClickListener { dialog, which ->
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                ab = ABNumber()
                ab.setAns()
                resultLog.text = "" //resultLog 清空
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                finish() // Activity運行結束
            }
        }
    }
}