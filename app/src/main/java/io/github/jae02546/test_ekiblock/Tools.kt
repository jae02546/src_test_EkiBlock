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

    //新しい問題を作成
    fun newGame(context: Context, pNo: Int, numAnswers: Int, numCards: Int, numItems: Int) {
        val qNoList = RoomMain.getQuestionNoList(context)
        if (qNoList.count() > 0) {
            //qListをシャッフルして問題を作成
            qNoList.shuffle()
            //問題として妥当性のある路線があるまで繰り返す
            for (v in qNoList) {
                var cNoList: MutableList<Int> = mutableListOf()
                val qRec = RoomMain.getQuestionRecord(context, v)
                if (qRec != null) {
                    //正解Noリスト取得
                    cNoList = newGameSelectSta(qRec, numAnswers, numCards, numItems)
                    //妥当性あり
                    if (cNoList.count() > 0) {
                        //正解リスト
                        val cList: MutableList<String> = mutableListOf()
                        for (v in cNoList) {
                            for (v2 in qRec.qiList) {
                                if (v == v2.iNo) {
                                    cList += v2.name
                                    break
                                }
                            }
                        }
                        //回答リストは空文字列で作成
                        val aList: MutableList<MutableList<String>> = mutableListOf()
                        for (v in 0 until numAnswers) {
                            val foo: MutableList<String> = mutableListOf()
                            for (v2 in 0 until numItems)
                                foo += ""
                            aList += foo
                        }
                        //持ち札リストはランダムに配置
                        val pList: MutableList<MutableList<String>> = mutableListOf()
                        val piece: MutableList<String> = mutableListOf()
                        for (v in cNoList) {
                            for (v2 in qRec.qiList) {
                                if (v == v2.iNo) {
                                    for (v3 in v2.name.indices) {
                                        piece += v2.name[v3].toString()
                                    }
                                    break
                                }
                            }
                        }
                        piece.shuffle()
                        for (v in 0 until numCards) {
                            val foo: MutableList<String> = mutableListOf()
                            for (v2 in 0 until numItems) {
                                val i = numItems * v + v2
                                foo += if (piece.count() > i) {
                                    piece[i]
                                } else {
                                    ""
                                }
                            }
                            pList += foo
                        }
                        //問い書込み
                        RoomMain.putLastStateRecord(
                            context,
                            LastStateTbl(pNo, qRec.qNo, cNoList, cList, aList, pList, false)
                        )

                        break
                    }
                }
            }
        }
    }

    private fun newGameSelectSta(
        qRec: QuestionTbl,
        numAnswers: Int,
        numCards: Int,
        numItems: Int
    ): MutableList<Int> {
        //路線の駅をシャッフルし先頭から最大5駅分を問題とする
        //ただし8文字以下の駅を対象とする
        //ただし合計文字数が24文字を超えない駅数とする
        val staList: MutableList<Int> = mutableListOf()
        val qiList: MutableList<QuestionItemTbl> = mutableListOf()
        for (v in qRec.qiList)
            qiList += v
        qiList.shuffle()
        var count = 0
        for (v in qiList) {
            //8文字以下
            if (v.name.length <= numItems) {
                count += v.name.length
                //24文字以下
                if (count <= numCards * numItems)
                    staList += v.iNo
                else
                    break
            }
            //最大5駅
            if (staList.count() >= numAnswers)
                break
        }

        return staList
    }

    //持ち札の移動を状態テーブルに書き込む
    fun swapPieces(
        context: Context,
        pNo: Int,
        fromAnswer: Boolean,
        fromRow: Int,
        fromColumn: Int,
        toAnswer: Boolean,
        toRow: Int,
        toColumn: Int
    ) {
        //レコードが無い場合は抜ける
        val lRec = RoomMain.getLastStateRecord(context, pNo) ?: return
        //行桁の位置がリスト範囲外なら抜ける
        if (fromAnswer) {
            if (lRec.aList.count() < fromRow || lRec.aList[0].count() < fromColumn) return
        } else {
            if (lRec.pList.count() < fromRow || lRec.pList[0].count() < fromColumn) return
        }
        if (toAnswer) {
            if (lRec.aList.count() < toRow || lRec.aList[0].count() < toColumn) return
        } else {
            if (lRec.pList.count() < toRow || lRec.pList[0].count() < toColumn) return
        }
        //移動は交換（移動先が空白でなかった場合は交換しないと消えてしまう）
        val from = if (fromAnswer)
            lRec.aList[fromRow][fromColumn]
        else
            lRec.pList[fromRow][fromColumn]
        val to = if (toAnswer)
            lRec.aList[toRow][toColumn]
        else
            lRec.pList[toRow][toColumn]
        if (fromAnswer)
            lRec.aList[fromRow][fromColumn] = to
        else
            lRec.pList[fromRow][fromColumn] = to
        if (toAnswer)
            lRec.aList[toRow][toColumn] = from
        else
            lRec.pList[toRow][toColumn] = from
        //書込み
        val rec = LastStateTbl(
            lRec.pNo,
            lRec.qNo,
            lRec.cNoList,
            lRec.cList,
            lRec.aList,
            lRec.pList,
            true,
        )
        RoomMain.putLastStateRecord(context, rec)
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