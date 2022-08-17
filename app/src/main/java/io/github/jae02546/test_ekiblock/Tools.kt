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

    //プレイ記録取得
    fun getPlayStatus(
        context: Context,
        playerName: MutableMap<Int, String>,
    ): MutableList<String> {
        //プレイヤー状態データクラス
        data class PlayStatus(
            //val playerNo: Int, //keyと被るので省略
            var name: String,
            var pCount: Int,
            var cCount: Int
        )

        //スコア取得
        val sList = RoomMain.getScoreList(context)

        //総プレイ数、総コンプ数
        var tpCount = 0
        var tcCount = 0
        for (v in sList) {
            tpCount += v.pCount
            tcCount += v.cCount
        }

        //<プレーヤNo,PlayStatus(プレーヤ名,プレイ数,comp数)>
        val ps: MutableMap<Int, PlayStatus> = mutableMapOf()
        //プレイヤー名
        for (i in 1..5)
            ps += if (playerName.containsKey(i))
                i to PlayStatus(playerName[i] ?: "", 0, 0)
            else
                i to PlayStatus("", 0, 0)


        //aTblからcomp数をカウント
        for (v in sList)
            if (ps.containsKey(v.pNo) && qMap.containsKey(v.qNo))
                if (v.aCompList == qMap[v.qNo]?.qiCompNoList)
                    ps[v.pNo]?.pCount = ps[v.pNo]?.pCount?.plus(1) ?: 0
        //fTblから最速comp数をカウント
        for (v in fsList)
            if (ps.containsKey(v.pNo))
                ps[v.pNo]?.cCount = ps[v.pNo]?.cCount?.plus(1) ?: 0


        //総プレイ数:nnn
        //総コンプ数:nnn
        //プレイヤー1
        //  プレイ数:nnn
        //  コンプ数:nnn
        //プレイヤー2
        //  プレイ数:nnn
        //  コンプ数:nnn
        //プレイヤー3
        //  プレイ数:nnn
        //  コンプ数:nnn
        //プレイヤー4
        //  プレイ数:nnn
        //  コンプ数:nnn
        //プレイヤー5
        //  プレイ数:nnn
        //  コンプ数:nnn
        val ret: MutableList<String> = mutableListOf()
        ret += "総プレイ数 $tpCount"
        ret += "総コンプ数 $tcCount"
        for (i in 1..5) {
            if (ps.containsKey(i)) {
                ret += "プレイヤー$i（" + (ps[i]?.name ?: "") + "）"
                ret += "プレイ数 " + (if (ps[i]?.pCount != null) ps[i]?.pCount.toString() else "0")
                ret += "コンプ数 " + (if (ps[i]?.cCount != null) ps[i]?.cCount.toString() else "0")
            } else {
                ret += "プレイヤー$i"
                ret += "プレイ数 0"
                ret += "コンプ数 0"
            }
        }

        return ret
    }


}