package com.applab.app_luckyindex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity() {
    val result_code = 101
    var luckyIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        // 接收 MainActivity 所傳來的 number 資料
        val number = intent.getStringExtra("number").toString().toInt()
        // 取得 Lucky Index 的資料
        luckyIndex = getLuckyIndex(number)
        // 將 luckyIndex 放到 View 元件(textResult)中
        textResult.text = luckyIndex.toString()
        // 將現在時間放到 View 元件(textTime)中
        // 時間格式轉換
        val time = SimpleDateFormat("HH : mm : ss", Locale.TAIWAN)
        textTime.text = time.format(Date())
    }
    fun onClick(view: View) {
        // 傳送資料到 MainActivity
        setResult(result_code, intent.putExtra("luckyIndex", luckyIndex.toString()))
        finish() // 將 ResultCodeActivity 畫面關閉
    }
}