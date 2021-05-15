package com.applab.app_nav1

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        // Bottom bar 切換時會變顏色
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment),
            drawer_layout // 加入 drawer 要設定
        )

        // 手動加入 action bar
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 底部 Bottom menu 的配置
        bottom_nav.setupWithNavController(navController)
        // Drawer menu 的配置
        nav_view.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener {
            if(it.itemId == R.id.item_exit) {
                AlertDialog.Builder(this)
                    .setTitle("確認視窗")
                    .setMessage("請問您確定要離開嗎？")
                    .setPositiveButton("確定", listener)
                    .setNegativeButton("取消", listener)
                    .show()
                true
            }
            false
        }

        nav_view.setNavigationItemSelectedListener(object: NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if(item.itemId == R.id.item_exit) {
                    AlertDialog.Builder(nav_view.context)
                        .setTitle("確認視窗")
                        .setMessage("請問您確定要離開嗎？")
                        .setPositiveButton("確定", listener)
                        .setNegativeButton("取消", listener)
                        .show()
                    return true
                }
                return false
            }
        })
    }

    // 實現 <- 的作用
    // 它會自動到堆疊中找出上一頁
    override fun onSupportNavigateUp(): Boolean {

        //return navController.navigateUp() || super.onSupportNavigateUp()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_info) {
            // 具有動畫效果的 menu
            val action = NavGraphDirections.actionGlobalInfoFragment()
            navController.navigate(action)
            return true
        }

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    private val listener = DialogInterface.OnClickListener{ _, which ->
        when(which) {
            DialogInterface.BUTTON_NEGATIVE -> {
            }
            DialogInterface.BUTTON_POSITIVE -> {
                finish() // Activity運行結束
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
}