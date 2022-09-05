package io.github.jae02546.test_ekiblock

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
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
                        for (v2 in cNoList) {
                            for (v3 in qRec.qiList) {
                                if (v2 == v3.iNo) {
                                    cList += v3.name
                                    break
                                }
                            }
                        }
                        //回答リストは空文字列で作成
                        val aList: MutableList<MutableList<String>> = mutableListOf()
                        for (v2 in 0 until numAnswers) {
                            val foo: MutableList<String> = mutableListOf()
                            for (v3 in 0 until numItems)
                                foo += ""
                            aList += foo
                        }
                        //持ち札リストはランダムに配置
                        val pList: MutableList<MutableList<String>> = mutableListOf()
                        val piece: MutableList<String> = mutableListOf()
                        for (v2 in cNoList) {
                            for (v3 in qRec.qiList) {
                                if (v2 == v3.iNo) {
                                    for (v4 in v3.name.indices) {
                                        piece += v3.name[v4].toString()
                                    }
                                    break
                                }
                            }
                        }
                        piece.shuffle()
                        for (v2 in 0 until numCards) {
                            val foo: MutableList<String> = mutableListOf()
                            for (v3 in 0 until numItems) {
                                val i = numItems * v2 + v3
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

    //ラスト状態テーブルの文字を交換する
    fun swapLastStateTblPiece(
        context: Context,
        pNo: Int,
        downPiece: MainLayout.PiecePara,
        upPiece: MainLayout.PiecePara
    ) {
        //downPieceとupPieceが同じなら抜ける
        if (downPiece == upPiece) return
        //レコードが無い場合は抜ける
        val lRec = RoomMain.getLastStateRecord(context, pNo) ?: return
        //行桁の位置がリスト範囲外なら抜ける
        if (downPiece.iy < 0 || downPiece.ix < 0 || upPiece.iy < 0 || upPiece.ix < 0) return
        if (downPiece.table) {
            if (lRec.tList.count() < downPiece.iy || lRec.tList[0].count() < downPiece.ix) return
        } else {
            if (lRec.cList.count() < downPiece.iy || lRec.cList[0].count() < downPiece.ix) return
        }
        if (upPiece.table) {
            if (lRec.tList.count() < upPiece.iy || lRec.tList[0].count() < upPiece.ix) return
        } else {
            if (lRec.cList.count() < upPiece.iy || lRec.cList[0].count() < upPiece.ix) return
        }

        //移動先に既に文字がある場合は間に挿入
        //ただし移動先に空きが無い場合は何もしない
        var fromList: MutableList<MutableList<String>> = mutableListOf()
        var toList: MutableList<MutableList<String>> = mutableListOf()

        if (downPiece.table && upPiece.table) {
            fromList = lRec.tList
            toList = lRec.tList
        } else if (!downPiece.table && upPiece.table) {
            fromList = lRec.cList
            toList = lRec.tList
        } else if (downPiece.table && !upPiece.table) {
            fromList = lRec.tList
            toList = lRec.cList
        } else if (!downPiece.table && !upPiece.table) {
            fromList = lRec.cList
            toList = lRec.cList
        }

        if (toList[upPiece.iy][upPiece.ix] == "") {
            //移動先が空きの場合はコピーして移動元を空きに
            toList[upPiece.iy][upPiece.ix] = fromList[downPiece.iy][downPiece.ix]
            fromList[downPiece.iy][downPiece.ix] = ""
        } else {
            //移動先が空きで無い場合は挿入して移動元を空きに
            val insStr = fromList[downPiece.iy][downPiece.ix]
            fromList[downPiece.iy][downPiece.ix] = ""
            //挿入位置から右方向に空きを探す
            var ins = false
            for (v in upPiece.ix until toList[upPiece.iy].count()) {
                if (toList[upPiece.iy][v] == "") {
                    toList[upPiece.iy].removeAt(v)
                    ins = true
                    break
                }
            }
            //右方向に空きが無ければ左方向に空きを探す
            if (!ins) {
                for (v in 0..upPiece.ix) {
                    if (toList[upPiece.iy][upPiece.ix - v] == "") {
                        toList[upPiece.iy].removeAt(upPiece.ix - v)
                        ins = true
                        break
                    }
                }
            }
            //移動先に挿入、空きが無い場合は文字を戻し何も無かったことに
            if (ins) {
                toList[upPiece.iy].add(upPiece.ix, insStr)
            } else {
                fromList[downPiece.iy][downPiece.ix] = insStr
            }
        }

        //書込み
        val rec = LastStateTbl(
            lRec.pNo,
            lRec.qNo,
            lRec.aNoList,
            lRec.aList,
            lRec.tList,
            lRec.cList,
            true,
        )
        RoomMain.putLastStateRecord(context, rec)
    }

    //開始状態取得
    fun isStarted(context: Context, pNo: Int): Boolean {
        val rec = RoomMain.getLastStateRecord(context, pNo)
        return rec?.started ?: false
    }

    //comp状態取得
    fun isComp(context: Context, pNo: Int): Boolean {
        val lRec = RoomMain.getLastStateRecord(context, pNo)
        val foo: MutableList<String> = mutableListOf()
        var count = 0
        if (lRec != null) {
            for (v in lRec.tList) {
                //これだと先頭に空文字列があってもcompになるが、まあいいか?
                //空文字列が間にあってもcompになるので駄目
                //val bar = v.joinToString("")
                //if (bar != "")
                //    foo += bar
                var bar = ""
                for (v2 in v) {
                    bar += if (v2 != "") v2 else " "
                }
                foo += bar.trimEnd() //後ろの空白は取る
            }
            for (v in lRec.aList) {
                for (v2 in foo) {
                    if (v == v2) {
                        count++
                        break
                    }
                }
            }
        }

        return count == lRec?.aList?.count() ?: false
    }

    //comp状態リスト取得
    fun getCompList(context: Context, pNo: Int): MutableList<QuestionItemTbl> {
        val ret: MutableList<QuestionItemTbl> = mutableListOf()
        val lRec = RoomMain.getLastStateRecord(context, pNo)
        if (lRec != null) {
            val qRec = RoomMain.getQuestionRecord(context, lRec.qNo)
            if (qRec != null) {
                val foo: MutableList<String> = mutableListOf()
                for (v in lRec.tList) {
                    val bar = v.joinToString("")
                    foo += bar
                }
                for (v in foo) {
                    var ng = true
                    for (v2 in 0 until qRec.qiList.count()) {
                        if (v == qRec.qiList[v2].name) {
                            ret += qRec.qiList[v2]
                            ng = false
                            break
                        }
                    }
                    if (ng) {
                        ret += QuestionItemTbl()
                    }
                }
            }
        }

        return ret
    }

    //プレイ回数インクリメント
    fun incPlayCount(context: Context, pNo: Int) {
        val rec = RoomMain.getScoreRecord(context, pNo)
        if (rec != null)
            RoomMain.putScoreRecord(context, ScoreTbl(pNo, rec.pCount + 1, rec.cCount))
        else
            RoomMain.putScoreRecord(context, ScoreTbl(pNo, 1, 0))
    }

    //コンプ回数インクリメント
    fun incCompCount(context: Context, pNo: Int) {
        val rec = RoomMain.getScoreRecord(context, pNo)
        if (rec != null)
            RoomMain.putScoreRecord(context, ScoreTbl(rec.pNo, rec.pCount, rec.cCount + 1))
    }

//    @RequiresApi(Build.VERSION_CODES.R)
//    fun getScreenSize(windowMetrics: WindowMetrics): MutableList<Int> {
//        val insets: Insets = windowMetrics.windowInsets
//            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
//        val foo: MutableList<Int> = mutableListOf()
//
//        //ScreenWidth
//        foo += windowMetrics.bounds.width()
//        //ScreenHeight
//        foo += windowMetrics.bounds.height()
//        //StatusBar
//        foo += insets.top
//        //NavigationBar
//        foo += insets.bottom
//
//        return foo
//    }

    fun getScreenSize(wm: WindowManager): MutableList<Int> {
        val disp = wm.defaultDisplay
        val realSize = Point()
        disp.getRealSize(realSize)

        val foo: MutableList<Int> = mutableListOf()
        foo += realSize.x
        foo += realSize.y

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