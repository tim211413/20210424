package com.applab.app_exam

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.ui.onNavDestinationSelected
import kotlinx.android.synthetic.main.content_main.*

// 包含學生姓名, 考試分數
data class User(val name: String, val score: Int) : java.io.Serializable // 序列化

class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    // 建立 user 資料集合
    // 其結果會放在 listview 物件中
    val users = mutableListOf(
        User("John", 100),
        User("Mary", 90),
        User("Jo", 45),
        User("Helen", 80),
        User("Tom", 55)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        context = this

        // FAB
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val intent = Intent(context, AddActivity::class.java)
            // 轉跳 Activity
            openResultActivityCustom.launch(intent)
        }

        //listView 的適配器
        val adapter = object: ArrayAdapter<User?>(
            context,
            R.layout.item_main, // android.R.id.simple_list_item_2
            R.id.text1, // 雖然用不到, 但是API 需要辨識到他
            users as List<User?>) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v =  super.getView(position, convertView, parent)
                val name = v.findViewById<View>(R.id.text1) as TextView
                val score = v.findViewById<View>(R.id.text2) as TextView
                name.text = getItem(position)?.name
                score.text = getItem(position)?.score.toString()
                if(getItem(position)?.score!! <60) {
                    score.setTextColor(Color.RED)
                }
                return v
            }
        }

        //listView 配置適配器
        list_view.adapter = adapter

        // listView onItemClick 監聽
        list_view.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val user = parent?.getItemAtPosition(position)
                val intent = Intent(context, AddActivity::class.java)
                intent.putExtra("user", user as User)
                intent.putExtra("position", position)
                // 轉跳 Activity
                openResultActivityCustom.launch(intent)
            }
        // listView onItemLongClick 監聽
        list_view.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { parent, view, position, id ->
                val user = parent?.getItemAtPosition(position)
                Toast.makeText(context, "delete : " + user.toString(), Toast.LENGTH_SHORT).show()
                adapter.remove(user as User)
                list_view.adapter = adapter // 重新配置 adapter
                true // 如果是true不會傳到 onClick  如果是 flase 會傳給onClick/
            }
    }

    private val openResultActivityCustom =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == 101) {
                val name = result.data?.getStringExtra("name").toString()
                val score = result.data?.getStringExtra("score").toString().toInt()
                val user = User(name, score)
                users.add(user)
                // 重整 adapter
                (list_view.adapter as ArrayAdapter<User>).notifyDataSetChanged()
            }
            else if(result.resultCode == 102) {
                val name = result.data?.getStringExtra("name").toString()
                val score = result.data?.getStringExtra("score").toString().toInt()
                val position = result.data?.getIntExtra("position", -1).toString().toInt()
                if(position != -1) {
                    val user = User(name, score)
                    users.set(position, user)
                    // 重整 adapter
                    (list_view.adapter as ArrayAdapter<User>).notifyDataSetChanged()
                }
            }
        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}