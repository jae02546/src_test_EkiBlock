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

//    //問いマップ
//    data class QuestionDc(
//        //val qNo: Int = 0, //keyと被るので省略
//        val name: String = "",
//        val kana: String = "",
//        val english: String = "",
//        val info1: String = "",
//        val info2: String = "",
//        val info3: String = "", //未使用
//        val url: String = "",
//        val qiList: MutableList<QuestionItemTbl> = mutableListOf(),
//    )
//
//    var qMap: MutableMap<Int, QuestionDc> = mutableMapOf()

    //回答をラスト状態テーブルに書き込む
    fun putAnswerToLastState(context: Context, pNo: Int, y: Int, x: Int, v: String) {
        //レコードが無い場合は抜ける
        val lRec = RoomMain.getLastStateRecord(context, pNo) ?: return
        //compならcTimeはそのまま
//        var ct = aRec.cTime
//        if (!isComp(context, pNo, qNo))
//            ct = ct.plusMillis(System.currentTimeMillis() - startTime)
//        //PlayerAnswer.dbに書込み
//        val at = AnswerTbl(
//            aRec.pNo,
//            aRec.qNo,
//            aRec.aList,
//            aRec.aCompList,
//            ct,
//            aRec.inPlay,
//            offSet,
//        )
//        RoomMain.putAnswerRecord(context, at)

    }

    //開始状態取得
    fun isStarted(context: Context, pNo: Int): Boolean {
        val lRec = RoomMain.getLastStateRecord(context, pNo)
        return lRec?.started ?: false
    }

    //comp状態取得
    fun isComp(context: Context, pNo: Int): Boolean {
        val lRec = RoomMain.getLastStateRecord(context, pNo)
        val foo: MutableList<String> = mutableListOf()
        var count = 0
        if (lRec != null) {
            for (v in lRec.aList) {
                val bar = v.joinToString("")
                if (bar != "")
                    foo += bar
            }
            for (v in lRec.cList) {
                for (v2 in foo) {
                    if (v == v2) {
                        count++
                        break
                    }
                }
            }
        }

        return count == lRec?.cList?.count() ?: false
    }


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
        for (v in 1..5) {
            var pn = ""
            var pc = 0
            var cc = 0
            pn = if (playerName.containsKey(v)) playerName[v] ?: "" else ""
            for (v2 in sList) {
                if (v2.pNo == v) {
                    pc = v2.pCount
                    cc = v2.cCount
                    break
                }
            }
            ps += v to PlayStatus(pn ?: "", pc, cc)
        }

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