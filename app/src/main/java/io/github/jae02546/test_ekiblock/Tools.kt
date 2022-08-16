package io.github.jae02546.test_ekiblock

import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import java.time.LocalDateTime

object Tools {
    //preferences読込String
    fun getPrefStr(context: Context, key: String, default: String): String {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getString(key, default) ?: ""
    }

    //preferences読込Int
    fun getPrefInt(context: Context, key: String, default: Int): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val str = sp.getString(key, default.toString())
        return str?.toInt() ?: default
    }

    //preferences読込Boolean
    fun getPrefBool(context: Context, key: String, default: Boolean): Boolean {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val str = sp.getString(key, default.toString())
        return str?.toBoolean() ?: default
    }

    //preferences読込LocalDateTime
    fun getPrefLocalDateTime(context: Context, key: String, default: LocalDateTime): LocalDateTime {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val str = sp.getString(key, default.toString())
        return LocalDateTime.parse(str)
    }

    //preferences書込String
    fun putPrefStr(context: Context, key: String, value: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    //preferences書込Int
    fun putPrefInt(context: Context, key: String, value: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    //preferences書込Boolean
    fun putPrefBool(context: Context, key: String, value: Boolean) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    //preferences書込LocalDateTime
    fun putPrefLocalDateTime(context: Context, key: String, value: LocalDateTime) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    //問いマップ
    data class QuestionDc(
        //val qNo: Int = 0, //keyと被るので省略
        val name: String = "",
        val kana: String = "",
        val english: String = "",
        val info1: String = "",
        val info2: String = "",
        val info3: String = "", //未使用
        val url: String = "",
        val qiList: MutableList<QuestionItemTbl> = mutableListOf(),
    )
    var qMap: MutableMap<Int, QuestionDc> = mutableMapOf()






    @RequiresApi(Build.VERSION_CODES.R)
    fun getScreenSize(windowMetrics: WindowMetrics): MutableList<Int> {
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        val foo: MutableList<Int> = mutableListOf()

        //ScreenWidth
        foo += windowMetrics.bounds.width()
        //ScreenHeight
        foo += windowMetrics.bounds.height()
        //StatusBar
        foo += insets.top
        //NavigationBar
        foo += insets.bottom

        return foo
    }

    /**
     * dpからpixelへの変換
     * @param dp
     * @param context
     * @return float pixel
     */
    fun convertDp2Px(dp: Float, context: Context): Float {
        val metrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        return dp * metrics.density
    }

    /**
     * pixelからdpへの変換
     * @param px
     * @param context
     * @return float dp
     */
    fun convertPx2Dp(px: Int, context: Context): Float {
        val metrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        return px / metrics.density
    }


}