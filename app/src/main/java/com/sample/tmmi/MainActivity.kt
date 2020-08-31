package com.sample.tmmi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

/**
 * 自启动其他app
 * @author 任家立
 * @date 2020/08/11
 */
class MainActivity : AppCompatActivity() {
    lateinit var shp : SharedPreferences

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shp = this.getSharedPreferences("sp",0)
        checkAndOpen()
        save.setOnClickListener{
            if (pack_name.text != null) {
                val ed = shp.edit().putString("package_name",pack_name.text.toString())
                ed.commit()
                checkAndOpen()
            }
        }
    }

    private fun checkAndOpen() {
        val packName = shp.getString("package_name", "Null")
        if (packName != "Null") {
            if (getPackageUid(this, packName!!) != -1) {
                openApp(packName)
                Process.killProcess(Process.myPid())
                exitProcess(0)
            }else{
                Toast.makeText(this,"此包名的程序在系统中不存在",Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * 获取已安装应用的 uid，-1 表示未安装此应用或程序异常
     * @param context
     * @param packageName
     * @return
     */
    private fun getPackageUid(context: Context, packageName: String): Int {
        return try {
            val applicationInfo = context.packageManager.getApplicationInfo(packageName, 0)
            applicationInfo.uid
        } catch (e: Exception) {
            -1
        }
    }

    /**
     * 打开指定包名的应用程序
     */
    private fun openApp(pkgName : String) {
        val starIntent = packageManager.getLaunchIntentForPackage(pkgName)
        if (starIntent != null) {
            starIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(starIntent)
        }
    }
}