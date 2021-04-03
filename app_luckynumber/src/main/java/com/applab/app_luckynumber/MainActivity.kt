package com.applab.app_luckynumber

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.applab.app_luckynumber.utils.LuckyNumber
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var luckyNumber = LuckyNumber()
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, luckyNumber.ans.toString())

    }
    // 按下 submit button 後所要執行的邏輯
    fun onClickSubmitButton(view: View) {
        val guess = inputNumber.text.toString().toInt()
        Log.d(TAG, guess.toString())
        //val ans = luckyNumber.ans
        val result = luckyNumber.validate(guess)
        Log.d(TAG, result.toString())
        if(result > 0) {
            // 通知 Toast
            Toast.makeText(this, "猜太大了", Toast.LENGTH_SHORT).show()
            // 通知 AlertDialog
            AlertDialog.Builder(this)
                .setTitle(R.string.title)
                .setMessage(R.string.too_big)
                .setPositiveButton(R.string.ok, listener2)
                .show()
        } else if(result < 0) {
            Toast.makeText(this, "猜太小了", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this)
                .setTitle(R.string.title)
                .setMessage(R.string.too_small)
                .setPositiveButton(R.string.ok, listener2)
                .show()
        } else {
            Toast.makeText(this, "猜對了, Activity運行結束", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this)
                .setTitle(R.string.title)
                .setMessage(R.string.bingo)
                .setPositiveButton(R.string.ok, null)
                .setPositiveButton(R.string.replay, listener)
                //.setNeutralButton(R.string.cancel, null)
                .setNegativeButton(R.string.exit, listener)
                .show()
            //finish() // Activity運行結束
            // 重新產生LuckyNumber() 物件
            luckyNumber = LuckyNumber()
        }

    }
    //實作一個 AlertDialog 的 onClick 監聽器
    val listener = DialogInterface.OnClickListener{ dialog, which ->
        when(which) {
            DialogInterface.BUTTON_POSITIVE -> {
                luckyNumber = LuckyNumber()
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                finish() // Activity運行結束
            }
        }
    }
    val listener2 = DialogInterface.OnClickListener() { dialog, which ->
        inputNumber.selectAll()
    }
}