package com.applab.app_recycler

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        var json = assets.open("csvjson.json").bufferedReader().use { it.readText() }
        // 將 json 字串轉成 List<Map<String, Object>>
        val listType: Type = object  : TypeToken<List<Map<String?, Object?>?>?>() {}.type
        val list: List<Map<String, Object>> = Gson().fromJson(json, listType)
        Log.d("MainActivity", list.toString())

        recycle_view.adapter = SalesAdapter(list)
        recycle_view.layoutManager = LinearLayoutManager(this)
        // recycler 優化 (固定 item 尺寸)
        recycle_view.setHasFixedSize(true)

    }
}