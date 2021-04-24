package com.applab.app_exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    var result_code = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        if(intent.getSerializableExtra("user") != null) {
            val user = intent.getSerializableExtra("user") as User
            et_name.setText(user.name.toString())
            et_score.setText(user.score.toString())
            button.setText("Update")
            result_code = 102
        }

    }
    fun onClick(view: View) {
        val name = et_name.text.toString()
        val score = et_score.text.toString()
        // 傳送資料到 MainActivity
        intent.putExtra("name", name)
        intent.putExtra("score", score)
        intent.getIntExtra("position", -1)
        setResult(result_code, intent)
        finish() // 將 ResultCodeActivity 畫面關閉
    }
}