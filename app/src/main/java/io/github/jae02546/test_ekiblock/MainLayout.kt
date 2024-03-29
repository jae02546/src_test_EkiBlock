package io.github.jae02546.test_ekiblock

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdSize


object MainLayout {
    //定数
    //メインitem数[score][question][answer][piece][newGame][adView]
    private const val numMains = 6

    //メインマージン
    private const val mMainMargin = 2

    //問題縦item数（高さに使用）
    private const val mQuestionHeight = 2

    //テーブル行数（最大駅数）
    private const val mAnswers = 5

    //持ち札エリア行数
    private const val mCardRows = 3

    //横item数
    private const val mItems = 8 //item幅計算時の分割数となる

    //Viewマージン それぞれのViewがこのマージンを取るので実際の間隔は倍となる
    //private const val mViewMargin = 10 //dp
    private const val mViewMargin = 2 //dp

    //adView高さ
    private const val mAdViewHeight = 50 //dp

    //レイアウトパラメータ
    var mPara: MutableList<MutableList<ItemPara>> = mutableListOf() //main
    var mcPara: MutableList<MutableList<ItemPara>> = mutableListOf() //main cursor
    var sPara: MutableList<MutableList<ItemPara>> = mutableListOf() //score
    var qPara: MutableList<MutableList<ItemPara>> = mutableListOf() //question
    var qiPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionItem
    var qlPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionLeft
    var qcPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionCenter
    var qrPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionRight
    var tPara: MutableList<MutableList<ItemPara>> = mutableListOf() //table
    var tiPara: MutableList<MutableList<ItemPara>> = mutableListOf() //tableItem
    var ttPara: MutableList<MutableList<ItemPara>> = mutableListOf() //tableTitle
    var tpPara: MutableList<MutableList<ItemPara>> = mutableListOf() //tablePiece
    var cPara: MutableList<MutableList<ItemPara>> = mutableListOf() //card
    var ciPara: MutableList<MutableList<ItemPara>> = mutableListOf() //cardItem
    var ctPara: MutableList<MutableList<ItemPara>> = mutableListOf() //cardTitle
    var cpPara: MutableList<MutableList<ItemPara>> = mutableListOf() //cardPiece
    var nPara: MutableList<MutableList<ItemPara>> = mutableListOf() //new game
    var adViewPara: MutableList<MutableList<ItemPara>> = mutableListOf() //adView

    //テーブル奇数行カラー、偶数行カラー
    private val oddColor = Color.argb(0x10, 0xff, 0xff, 0x00)
    private val evenColor = Color.argb(0x10, 0x00, 0xff, 0x00)

    enum class EnumViewType {
        ConstraintLayout,
        TextView,
        AdView,
    }

    data class ItemPara(
        var id: Int = 0,
        var width: Int = 0,
        var height: Int = 0,
        var marginLeft: Int = 0,
        var marginRight: Int = 0,
        var marginTop: Int = 0,
        var marginBottom: Int = 0,
        var layerDrawable: LayerDrawable = LayerDrawable(arrayOf<Drawable>()),
        var viewType: EnumViewType,
        var verticalWeight: Float = 0f, //0はWeight無し
        var fontSize: Float = 0f, //0はデフォルト
        var fontColor: Int = 0, //0はデフォルト
        var gravity: Int = Gravity.CENTER,
        var text: String = "",
    )

    @ColorInt
    fun Context.getThemeColor(
        @AttrRes themeAttrId: Int
    ): Int {
        return obtainStyledAttributes(
            intArrayOf(themeAttrId)
        ).use {
            it.getColor(0, Color.TRANSPARENT)
        }
    }

    //レイアウト作成
    fun makeLayout(context: Context, screenSize: MutableList<Int>): ConstraintLayout {
        //パラメータ初期化
        mPara = mutableListOf()
        mcPara = mutableListOf()
        sPara = mutableListOf()
        qPara = mutableListOf()
        qiPara = mutableListOf()
        qlPara = mutableListOf()
        qcPara = mutableListOf()
        qrPara = mutableListOf()
        tPara = mutableListOf()
        tiPara = mutableListOf()
        ttPara = mutableListOf()
        tpPara = mutableListOf()
        cPara = mutableListOf()
        ciPara = mutableListOf()
        ctPara = mutableListOf()
        cpPara = mutableListOf()
        nPara = mutableListOf()
        adViewPara = mutableListOf()

        //item基本幅
        val iWidth = screenSize[0] / mItems

        //main[score][question][answer][piece][newGame][adView]
        val mlMargin: MutableList<Int> = mutableListOf(
            mMainMargin * 2,
            mMainMargin * 2,
            mMainMargin * 2,
            mMainMargin * 2,
            mMainMargin * 2,
            0
        )
        val mrMargin: MutableList<Int> = mutableListOf(
            mMainMargin * 2,
            mMainMargin * 2,
            mMainMargin * 2,
            mMainMargin * 2,
            mMainMargin * 2,
            0
        )
        val mtMargin: MutableList<Int> = mutableListOf(
            mMainMargin * 2,
            mMainMargin,
            mMainMargin,
            mMainMargin,
            mMainMargin,
            0
        )
        val mbMargin: MutableList<Int> = mutableListOf(
            mMainMargin,
            mMainMargin,
            mMainMargin,
            mMainMargin,
            mMainMargin * 2,
            0
        )
        for (v in 0 until numMains) {
            val height = when (v) {
                1 -> { //問題
                    iWidth * mQuestionHeight - iWidth / 2 //- iWidth / 2 は調整
                }
                2 -> { //回答
                    iWidth * mAnswers + iWidth / 2 //+ iWidth / 2 はタイトル
                }
                3 -> { //持ち札
                    iWidth * mCardRows + iWidth / 2 //+ iWidth / 2 はタイトル
                }
                5 -> { //adView
                    Tools.convertDp2Px(mAdViewHeight.toFloat(), context).toInt()
                }
                else -> { //score newGame
                    0
                }
            }
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, height,
                    mlMargin[v], mrMargin[v], mtMargin[v], mbMargin[v],
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            mPara += foo
        }
        var mLayout = ConstraintLayout(context)
        mLayout = getConstraintLayout(mLayout, mPara)
        //カーソル用textBox追加
        val gdCursor = GradientDrawable()
        gdCursor.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
//            context.getThemeColor(R.attr.colorButtonNormal)
            Color.RED
        )
        val ldCursor = LayerDrawable(arrayOf<Drawable>(gdCursor))
        ldCursor.setLayerInset(0, 0, 0, 0, 0)
//        val cCursor = context.getThemeColor(R.attr.editTextColor)
        val cCursor = Color.RED
        val wCursor =
            (screenSize[0] - (mMainMargin * 4) - (mViewMargin * (mItems * 2 + 2))) / mItems
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    wCursor, wCursor,
                    0, 0, 0, 0,
                    //ldCursor,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.TextView, 0f, 0f, cCursor, Gravity.CENTER,
                    //EnumViewType.TextView, 0f, 0f, cCursor, Gravity.START + Gravity.TOP,
                    //EnumViewType.TextView, 0f, 0f, cCursor, Gravity.CENTER_HORIZONTAL + Gravity.TOP,
                    ""
                )
            }
            mcPara += foo
        }
        mLayout = getConstraintLayout(mLayout, mcPara)
        val mc = mLayout.getViewById(mcPara[0][0].id) as TextView
        mc.isVisible = false //非表示にしておく
        //MATCH_PARENT
        mLayout.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )

        //score
        val gdScore = GradientDrawable()
        gdScore.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdScore.setColor(context.getThemeColor(R.attr.colorButtonNormal))
        val ldScore = LayerDrawable(arrayOf<Drawable>(gdScore))
        ldScore.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldScore,
                    EnumViewType.TextView, 0f, 0f, 0, Gravity.CENTER,
                    //v.toString() + v2.toString()
                    ""
                )
            }
            sPara += foo
        }
        var sLayout = mLayout.getViewById(mPara[0][0].id) as ConstraintLayout
        sLayout = getConstraintLayout(sLayout, sPara)

        //question
        val gdQuestion = GradientDrawable()
        gdQuestion.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdQuestion.setColor(context.getThemeColor(R.attr.colorButtonNormal))
        val ldQuestion = LayerDrawable(arrayOf<Drawable>(gdQuestion))
        ldQuestion.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldQuestion,
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            qPara += foo
        }
        var qLayout = mLayout.getViewById(mPara[1][0].id) as ConstraintLayout
        qLayout = getConstraintLayout(qLayout, qPara)

        //question item [left][center][right]
        for (v in 0..0) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..2) {
                vPara += ItemPara(
                    View.generateViewId(),
                    if (v2 != 1) iWidth else 0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    //v.toString() + v2.toString()
                    ""
                )
            }
            qiPara += vPara
        }
        var qiLayout = qLayout.getViewById(qPara[0][0].id) as ConstraintLayout
        qiLayout = getConstraintLayout(qiLayout, qiPara)

        //question left [ヒント][解答駅数]
        val qlWeight: MutableList<Float> = mutableListOf(1.0f, 2.0f)
        for (v in 0..1) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.TextView,
                    qlWeight[v],
                    10f,
                    0,
                    Gravity.CENTER,
                    //v.toString() + v2.toString()
                    ""
                )
            }
            qlPara += vPara
        }
        var qlLayout = qiLayout.getViewById(qiPara[0][0].id) as ConstraintLayout
        qlLayout = getConstraintLayout(qlLayout, qlPara)

        //question center [かな][駅名][ローマ字][補足1+2]
        val qcColor = context.getThemeColor(R.attr.editTextColor)
        val qcWeight: MutableList<Float> = mutableListOf(1.0f, 1.0f, 1.0f, 1.0f)
        val qcFontSize: MutableList<Float> = mutableListOf(10f, 0f, 10f, 10f)
        val qcGravity: MutableList<Int> =
            mutableListOf(
                Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM,
                Gravity.CENTER,
                Gravity.CENTER_HORIZONTAL + Gravity.TOP,
                Gravity.CENTER
            )
        for (v in 0..3) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.TextView,
                    qcWeight[v],
                    qcFontSize[v],
                    if (v != 1) 0 else qcColor,
                    qcGravity[v],
                    //v.toString() + v2.toString()
                    ""
                )
            }
            qcPara += vPara
        }
        var qcLayout = qiLayout.getViewById(qiPara[0][1].id) as ConstraintLayout
        qcLayout = getConstraintLayout(qcLayout, qcPara)

        //question right [補足3]
        for (v in 0..0) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.TextView,
                    0f,
                    10f,
                    0,
                    Gravity.CENTER,
                    //v.toString() + v2.toString()
                    ""
                )
            }
            qrPara += vPara
        }
        var qrLayout = qiLayout.getViewById(qiPara[0][2].id) as ConstraintLayout
        qrLayout = getConstraintLayout(qrLayout, qrPara)

        //tle
        val gdAnswer = GradientDrawable()
        gdAnswer.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdAnswer.setColor(context.getThemeColor(R.attr.colorSecondaryVariant))
        val ldAnswer = LayerDrawable(arrayOf<Drawable>(gdAnswer))
        ldAnswer.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldAnswer,
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            tPara += foo
        }
        var tLayout = mLayout.getViewById(mPara[2][0].id) as ConstraintLayout
        tLayout = getConstraintLayout(tLayout, tPara)

        //table item
        for (v in 0..1) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, if (v == 0) iWidth / 2 else 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            tiPara += foo
        }
        var tiLayout = tLayout.getViewById(tPara[0][0].id) as ConstraintLayout
        tiLayout = getConstraintLayout(tiLayout, tiPara)

        //table title
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
//                    EnumViewType.TextView, 0f, 10f, 0, Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM,
                    EnumViewType.TextView, 0f, 10f, 0, Gravity.CENTER,
                    "テーブル"
                )
            }
            ttPara += foo
        }
        var ttLayout = tiLayout.getViewById(tiPara[0][0].id) as ConstraintLayout
        ttLayout = getConstraintLayout(ttLayout, ttPara)

        //table piece
        val gdAnswerPiece = GradientDrawable()
        gdAnswerPiece.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //gdAnswerPiece.setColor(context.getThemeColor(R.attr.colorOnPrimary)) //これだ
        gdAnswerPiece.setColor(oddColor)
        val ldAnswerPiece = LayerDrawable(arrayOf<Drawable>(gdAnswerPiece))
        ldAnswerPiece.setLayerInset(0, 0, 0, 0, 0)

        val gdAnswerPiece2 = GradientDrawable()
        gdAnswerPiece2.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //gdAnswerPiece.setColor(context.getThemeColor(R.attr.colorOnPrimary)) //これだ
        gdAnswerPiece2.setColor(evenColor)
        val ldAnswerPiece2 = LayerDrawable(arrayOf<Drawable>(gdAnswerPiece2))
        ldAnswerPiece2.setLayerInset(0, 0, 0, 0, 0)

        val apc = context.getThemeColor(R.attr.editTextColor)
        for (v in 0 until mAnswers) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0 until mItems) {
                val lMargin = if (v2 != 0) mViewMargin else mViewMargin * 2
                val rMargin = if (v2 != mItems - 1) mViewMargin else mViewMargin * 2
                val tMargin = if (v != 0) mViewMargin else mViewMargin * 2
                val bMargin = if (v != mAnswers - 1) mViewMargin else mViewMargin * 2
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, tMargin, bMargin,
                    if (v % 2 == 1) ldAnswerPiece else ldAnswerPiece2,
                    EnumViewType.TextView, 0f, 0f, apc, Gravity.CENTER,
                    //v.toString() + v2.toString()
                    ""
                )
            }
            tpPara += vPara
        }
        var tpLayout = tiLayout.getViewById(tiPara[1][0].id) as ConstraintLayout
        tpLayout = getConstraintLayout(tpLayout, tpPara)

        //card
        val gdCard = GradientDrawable()
        gdCard.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdPiece.setColor(context.getThemeColor(R.attr.colorPrimaryVariant))
        val ldCard = LayerDrawable(arrayOf<Drawable>(gdCard))
        ldCard.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldCard,
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            cPara += foo
        }
        var cLayout = mLayout.getViewById(mPara[3][0].id) as ConstraintLayout
        cLayout = getConstraintLayout(cLayout, cPara)

        //card item
        for (v in 0..1) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, if (v == 0) iWidth / 2 else 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            ciPara += foo
        }
        var ciLayout = cLayout.getViewById(cPara[0][0].id) as ConstraintLayout
        ciLayout = getConstraintLayout(ciLayout, ciPara)

        //card title
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
//                    EnumViewType.TextView, 0f, 10f, 0, Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM,
                    EnumViewType.TextView, 0f, 10f, 0, Gravity.CENTER,
                    "持ち札"
                )
            }
            ctPara += foo
        }
        var ctLayout = ciLayout.getViewById(ciPara[0][0].id) as ConstraintLayout
        ctLayout = getConstraintLayout(ctLayout, ctPara)

        //card piece
        val gdCardPiece = GradientDrawable()
        gdCardPiece.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        gdCardPiece.setColor(context.getThemeColor(R.attr.colorOnPrimary)) //これだ
        val ldCardPiece = LayerDrawable(arrayOf<Drawable>(gdCardPiece))
        ldCardPiece.setLayerInset(0, 0, 0, 0, 0)
        val cpc = context.getThemeColor(R.attr.editTextColor)
        for (v in 0 until mCardRows) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0 until mItems) {
                val lMargin = if (v2 != 0) mViewMargin else mViewMargin * 2
                val rMargin = if (v2 != mItems - 1) mViewMargin else mViewMargin * 2
                val tMargin = if (v != 0) mViewMargin else mViewMargin * 2
                val bMargin = if (v != mCardRows - 1) mViewMargin else mViewMargin * 2
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, tMargin, bMargin,
                    ldCardPiece,
                    EnumViewType.TextView, 0f, 0f, cpc, Gravity.CENTER,
                    //v.toString() + v2.toString()
                    ""
                )
            }
            cpPara += vPara
        }
        var cpLayout = ciLayout.getViewById(ciPara[1][0].id) as ConstraintLayout
        cpLayout = getConstraintLayout(cpLayout, cpPara)

        //newGame
        val gdNewGame = GradientDrawable()
        gdNewGame.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdNewGame.setColor(context.getThemeColor(R.attr.colorButtonNormal))
        val ldNewGame = LayerDrawable(arrayOf<Drawable>(gdNewGame))
        ldNewGame.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldNewGame,
                    EnumViewType.TextView, 0f, 0f, 0, Gravity.CENTER,
                    "新しいゲーム" //"new game"
                )
            }
            nPara += foo
        }
        var nLayout = mLayout.getViewById(mPara[4][0].id) as ConstraintLayout
        nLayout = getConstraintLayout(nLayout, nPara)

        //adView
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.AdView, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            adViewPara += foo
        }
        var adLayout = mLayout.getViewById(mPara[5][0].id) as ConstraintLayout
        adLayout = getAdViewLayout(adLayout, adViewPara)

        return mLayout
    }

    fun getConstraintLayout(
        layout: ConstraintLayout,
        para: MutableList<MutableList<ItemPara>>
    ): ConstraintLayout {

        for (v in para) {
            for (v2 in v) {
                when (v2.viewType) {
                    EnumViewType.ConstraintLayout -> {
                        val child = ConstraintLayout(layout.context)
                        child.id = v2.id
                        child.background = v2.layerDrawable
                        layout.addView(child)
                    }
                    EnumViewType.TextView -> {
                        val child = TextView(layout.context)
                        child.id = v2.id
                        child.background = v2.layerDrawable
                        child.gravity = v2.gravity
                        child.typeface = Typeface.MONOSPACE
                        if (v2.fontSize != 0f)
                            child.textSize = v2.fontSize
                        if (v2.fontColor != 0)
                            child.setTextColor(v2.fontColor)
                        child.text = v2.text
                        layout.addView(child)
                    }
                    else -> {
                        //何もしない
                    }
                }
            }
        }

        val cs = ConstraintSet()
        cs.clone(layout)
        val cs2: ConstraintSet = getConstraintSetConnect(cs, para)
        cs2.applyTo(layout)

        return layout
    }

    private fun getAdViewLayout(
        layout: ConstraintLayout,
        para: MutableList<MutableList<ItemPara>>
    ): ConstraintLayout {

        val child = com.google.android.gms.ads.AdView(layout.context)
        child.id = para[0][0].id
        child.setAdSize(AdSize.BANNER)
        child.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        layout.addView(child)

        //test "ca-app-pub-3940256099942544/6300978111"

        val cs = ConstraintSet()
        cs.clone(layout)
        val cs2 = getConstraintSetConnect(cs, para)
        cs2.applyTo(layout)

        return layout
    }

    private fun getConstraintSetConnect(
        cs: ConstraintSet,
        para: MutableList<MutableList<ItemPara>>
    ): ConstraintSet {

        for (v in 0 until para.count()) {
            for (v2 in 0 until para[v].count()) {

                if (para[v][v2].width == 0)
                    cs.constrainWidth(para[v][v2].id, ConstraintSet.MATCH_CONSTRAINT)
                else
                    cs.constrainWidth(para[v][v2].id, para[v][v2].width)
                if (para[v][v2].height == 0)
                    cs.constrainHeight(para[v][v2].id, ConstraintSet.MATCH_CONSTRAINT)
                else
                    cs.constrainHeight(para[v][v2].id, para[v][v2].height)
                if (para[v][v2].verticalWeight != 0f) {
                    cs.setVerticalChainStyle(para[v][v2].id, ConstraintSet.CHAIN_SPREAD)
                    cs.setVerticalWeight(para[v][v2].id, para[v][v2].verticalWeight)
                }

                when (v) {
                    0 -> {
                        //■■■
                        //□□□
                        //□□□
                        cs.connect(
                            para[v][v2].id,
                            ConstraintSet.TOP,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.TOP,
                            para[v][v2].marginTop
                        )
                        if (para.count() > 1) {
                            cs.connect(
                                para[v][v2].id,
                                ConstraintSet.BOTTOM,
                                para[v + 1][v2].id,
                                ConstraintSet.TOP,
                                para[v][v2].marginBottom
                            )
                        } else {
                            cs.connect(
                                para[v][v2].id,
                                ConstraintSet.BOTTOM,
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.BOTTOM,
                                para[v][v2].marginBottom
                            )
                        }
                        when (v2) {
                            0 -> {
                                //■□□
                                //□□□
                                //□□□
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.LEFT,
                                    ConstraintSet.PARENT_ID,
                                    ConstraintSet.LEFT,
                                    para[v][v2].marginLeft
                                )
                                if (para[v].count() > 1) {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.RIGHT,
                                        para[v][v2 + 1].id,
                                        ConstraintSet.LEFT,
                                        para[v][v2].marginRight
                                    )
                                } else {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.RIGHT,
                                        ConstraintSet.PARENT_ID,
                                        ConstraintSet.RIGHT,
                                        para[v][v2].marginRight
                                    )
                                }
                            }
                            para[v].count() - 1 -> {
                                //□□■
                                //□□□
                                //□□□
                                if (para[v].count() > 1) {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.LEFT,
                                        para[v][v2 - 1].id,
                                        ConstraintSet.RIGHT,
                                        para[v][v2].marginLeft
                                    )
                                } else {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.LEFT,
                                        ConstraintSet.PARENT_ID,
                                        ConstraintSet.LEFT,
                                        para[v][v2].marginLeft
                                    )
                                }
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.RIGHT,
                                    ConstraintSet.PARENT_ID,
                                    ConstraintSet.RIGHT,
                                    para[v][v2].marginRight
                                )
                            }
                            else -> {
                                //□■□
                                //□□□
                                //□□□
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.LEFT,
                                    para[v][v2 - 1].id,
                                    ConstraintSet.RIGHT,
                                    para[v][v2].marginLeft
                                )
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.RIGHT,
                                    para[v][v2 + 1].id,
                                    ConstraintSet.LEFT,
                                    para[v][v2].marginRight
                                )
                            }
                        }
                    }
                    para.count() - 1 -> {
                        //□□□
                        //□□□
                        //■■■
                        if (para.count() > 1) {
                            cs.connect(
                                para[v][v2].id,
                                ConstraintSet.TOP,
                                para[v - 1][v2].id,
                                ConstraintSet.BOTTOM,
                                para[v][v2].marginTop
                            )
                        } else {
                            cs.connect(
                                para[v][v2].id,
                                ConstraintSet.TOP,
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.TOP,
                                para[v][v2].marginTop
                            )
                        }
                        cs.connect(
                            para[v][v2].id,
                            ConstraintSet.BOTTOM,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.BOTTOM,
                            para[v][v2].marginBottom
                        )
                        when (v2) {
                            0 -> {
                                //□□□
                                //□□□
                                //■□□
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.LEFT,
                                    ConstraintSet.PARENT_ID,
                                    ConstraintSet.LEFT,
                                    para[v][v2].marginLeft
                                )
                                if (para[v].count() > 1) {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.RIGHT,
                                        para[v][v2 + 1].id,
                                        ConstraintSet.LEFT,
                                        para[v][v2].marginRight
                                    )
                                } else {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.RIGHT,
                                        ConstraintSet.PARENT_ID,
                                        ConstraintSet.RIGHT,
                                        para[v][v2].marginRight
                                    )
                                }
                            }
                            para[v].count() - 1 -> {
                                //□□□
                                //□□□
                                //□□■
                                if (para[v].count() > 1) {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.LEFT,
                                        para[v][v2 - 1].id,
                                        ConstraintSet.RIGHT,
                                        para[v][v2].marginLeft
                                    )
                                } else {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.LEFT,
                                        ConstraintSet.PARENT_ID,
                                        ConstraintSet.LEFT,
                                        para[v][v2].marginLeft
                                    )
                                }
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.RIGHT,
                                    ConstraintSet.PARENT_ID,
                                    ConstraintSet.RIGHT,
                                    para[v][v2].marginRight
                                )
                            }
                            else -> {
                                //□□□
                                //□□□
                                //□■□
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.LEFT,
                                    para[v][v2 - 1].id,
                                    ConstraintSet.RIGHT,
                                    para[v][v2].marginLeft
                                )
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.RIGHT,
                                    para[v][v2 + 1].id,
                                    ConstraintSet.LEFT,
                                    para[v][v2].marginRight
                                )
                            }
                        }
                    }
                    else -> {
                        //□□□
                        //■■■
                        //□□□
                        cs.connect(
                            para[v][v2].id,
                            ConstraintSet.TOP,
                            para[v - 1][v2].id,
                            ConstraintSet.BOTTOM,
                            para[v][v2].marginTop
                        )
                        cs.connect(
                            para[v][v2].id,
                            ConstraintSet.BOTTOM,
                            para[v + 1][v2].id,
                            ConstraintSet.TOP,
                            para[v][v2].marginBottom
                        )
                        when (v2) {
                            0 -> {
                                //□□□
                                //■□□
                                //□□□
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.LEFT,
                                    ConstraintSet.PARENT_ID,
                                    ConstraintSet.LEFT,
                                    para[v][v2].marginLeft
                                )
                                if (para[v].count() > 1) {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.RIGHT,
                                        para[v][v2 + 1].id,
                                        ConstraintSet.LEFT,
                                        para[v][v2].marginRight
                                    )
                                } else {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.RIGHT,
                                        ConstraintSet.PARENT_ID,
                                        ConstraintSet.RIGHT,
                                        para[v][v2].marginRight
                                    )
                                }
                            }
                            para[v].count() - 1 -> {
                                //□□□
                                //□□■
                                //□□□
                                if (para[v].count() > 1) {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.LEFT,
                                        para[v][v2 - 1].id,
                                        ConstraintSet.RIGHT,
                                        para[v][v2].marginLeft
                                    )
                                } else {
                                    cs.connect(
                                        para[v][v2].id,
                                        ConstraintSet.LEFT,
                                        ConstraintSet.PARENT_ID,
                                        ConstraintSet.LEFT,
                                        para[v][v2].marginLeft
                                    )
                                }
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.RIGHT,
                                    ConstraintSet.PARENT_ID,
                                    ConstraintSet.RIGHT,
                                    para[v][v2].marginRight
                                )
                            }
                            else -> {
                                //□□□
                                //□■□
                                //□□□
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.LEFT,
                                    para[v][v2 - 1].id,
                                    ConstraintSet.RIGHT,
                                    para[v][v2].marginLeft
                                )
                                cs.connect(
                                    para[v][v2].id,
                                    ConstraintSet.RIGHT,
                                    para[v][v2 + 1].id,
                                    ConstraintSet.LEFT,
                                    para[v][v2].marginRight
                                )
                            }
                        }
                    }
                }
            }
        }

        return cs
    }

    //レイアウト表示（各項目の表示）
    fun showLayout(
        layout: ConstraintLayout,
        pNo: Int,
        newGame: Boolean,
    ) {
        //ラスト状態の取得と新ゲーム、スコア更新
        var lRec = RoomMain.getLastStateRecord(layout.context, pNo)
        //ラスト状態が無い場合も新ゲーム作成
        if (lRec == null || newGame) {
            //初期起動時、一度以上操作があった場合はスコア更新
            if (lRec == null || Tools.isStarted(layout.context, pNo))
                Tools.incPlayCount(layout.context, pNo) //プレイ回数インクリメント
            //新ゲーム作成
            Tools.newGame(layout.context, pNo, mAnswers, mCardRows, mItems)
            //再読み込み
            lRec = RoomMain.getLastStateRecord(layout.context, pNo)
//            //選択解除
//            deselect(layout)
        }
        //スコア表示
        val sRec = RoomMain.getScoreRecord(layout.context, pNo)
        val stv = layout.findViewById<TextView>(sPara[0][0].id)
        if (sRec != null) {
            val foo = "プレイ数:" + sRec.pCount.toString() + "\nコンプ数:" + sRec.cCount.toString()
            stv.text = foo
        } else {
            val foo = "プレイ数:0\nコンプ数:0"
            stv.text = foo
        }
        //問い表示
        if (lRec != null) {
            val qRec = RoomMain.getQuestionRecord(layout.context, lRec.qNo)
            if (qRec != null) {
                val hint = layout.findViewById<TextView>(qlPara[0][0].id)
                val foo =
                    if (qRec.url != "") "<a href=" + (qRec.url) + ">" + "Hint" + "</a>" else ""
                hint.text = HtmlCompat.fromHtml(foo, HtmlCompat.FROM_HTML_MODE_COMPACT)
                hint.movementMethod = LinkMovementMethod.getInstance()
                val ans = layout.findViewById<TextView>(qlPara[1][0].id)
                val bar = "回答数\n" + lRec.aList.count() + "駅"
                ans.text = bar
                val kana = layout.findViewById<TextView>(qcPara[0][0].id)
                kana.text = qRec.kana
                val name = layout.findViewById<TextView>(qcPara[1][0].id)
                name.text = qRec.name
                val english = layout.findViewById<TextView>(qcPara[2][0].id)
                english.text = qRec.english
                val info1 = layout.findViewById<TextView>(qcPara[3][0].id)
                info1.text = qRec.info1
                val info2 = layout.findViewById<TextView>(qrPara[0][0].id)
                info2.text = qRec.info2
            } else {
                //ここにはこないはず
                for (v in qcPara) {
                    for (v2 in v) {
                        val foo = layout.findViewById<TextView>(v2.id)
                        foo.text = ""
                    }
                }
            }
        } else {
            //ここにはこないはず
            for (v in qcPara) {
                for (v2 in v) {
                    val foo = layout.findViewById<TextView>(v2.id)
                    foo.text = ""
                }
            }
        }
        //回答表示
        if (lRec != null) {
            for (v in 0 until mAnswers) {
                for (v2 in 0 until mItems) {
                    val atv = layout.findViewById<TextView>(tpPara[v][v2].id)
                    if (lRec.tList.count() > v && lRec.tList[v].count() > v2) {
                        atv.text = lRec.tList[v][v2]
                    } else {
                        atv.text = ""
                    }
                }
            }
        }
        //持ち札表示
        if (lRec != null) {
            for (v in 0 until mCardRows) {
                for (v2 in 0 until mItems) {
                    val ptv = layout.findViewById<TextView>(cpPara[v][v2].id)
                    if (lRec.cList.count() > v && lRec.cList[v].count() > v2) {
                        ptv.text = lRec.cList[v][v2]
                    } else {
                        ptv.text = ""
                    }
                }
            }
        }
    }

    data class PiecePara(
        var table: Boolean,
        var ix: Int,
        var iy: Int,
    )

    fun getUpPiece(
        layout: ConstraintLayout,
        downPiece: PiecePara,
        x: Int, //up座標x offset済み
        y: Int, //up座標y offset済み 指に隠れないように調整済み
    ): PiecePara {
        data class PosSize(
            val xs: Int,
            val ys: Int,
            val xe: Int,
            val ye: Int,
            val w: Int,
            val h: Int,
        )
        //answer offset
        val aosX = layout.findViewById<ConstraintLayout>(mPara[2][0].id).x
        var aosY = layout.findViewById<ConstraintLayout>(mPara[2][0].id).y
        aosY += layout.findViewById<ConstraintLayout>(tiPara[1][0].id).y
        //card offset
        val cosX = layout.findViewById<ConstraintLayout>(mPara[3][0].id).x
        var cosY = layout.findViewById<ConstraintLayout>(mPara[3][0].id).y
        cosY += layout.findViewById<ConstraintLayout>(ciPara[1][0].id).y
        //各pieceの相対座標
        val aPS: MutableList<MutableList<PosSize>> = mutableListOf()
        for (v in 0 until tpPara.count()) {
            val foo: MutableList<PosSize> = mutableListOf()
            for (v2 in 0 until tpPara[v].count()) {
                val bar = layout.findViewById<TextView>(tpPara[v][v2].id)
                foo += PosSize(
                    (aosX + bar.x).toInt(),
                    (aosY + bar.y).toInt(),
                    (aosX + bar.x + bar.width).toInt(),
                    (aosY + bar.y + bar.height).toInt(),
                    bar.width,
                    bar.height
                )
            }
            aPS += foo
        }
        val cPS: MutableList<MutableList<PosSize>> = mutableListOf()
        for (v in 0 until cpPara.count()) {
            val foo: MutableList<PosSize> = mutableListOf()
            for (v2 in 0 until cpPara[v].count()) {
                val bar = layout.findViewById<TextView>(cpPara[v][v2].id)
                foo += PosSize(
                    (cosX + bar.x).toInt(),
                    (cosY + bar.y).toInt(),
                    (cosX + bar.x + bar.width).toInt(),
                    (cosY + bar.y + bar.height).toInt(),
                    bar.width,
                    bar.height
                )
            }
            cPS += foo
        }
        //座標がマスの中心になるよう調整
        val x2 =
            x + (if (downPiece.table) aPS[downPiece.iy][downPiece.ix].w else cPS[downPiece.iy][downPiece.ix].w) / 2
        val y2 =
            y + (if (downPiece.table) aPS[downPiece.iy][downPiece.ix].h else cPS[downPiece.iy][downPiece.ix].h) / 2
        //どのマスの上か検索
        val ret = PiecePara(false, -1, -1)
        for (v in 0 until aPS.count())
            for (v2 in 0 until aPS[v].count()) {
                if (x2 >= aPS[v][v2].xs && x2 <= aPS[v][v2].xe && y2 >= aPS[v][v2].ys && y2 <= aPS[v][v2].ye) {
                    ret.table = true
                    ret.ix = v2
                    ret.iy = v
                    break
                }
                if (ret.ix > 0) break
            }
        if (ret.ix > 0) return ret
        for (v in 0 until cPS.count())
            for (v2 in 0 until cPS[v].count()) {
                if (x2 >= cPS[v][v2].xs && x2 <= cPS[v][v2].xe && y2 >= cPS[v][v2].ys && y2 <= cPS[v][v2].ye) {
                    ret.table = false
                    ret.ix = v2
                    ret.iy = v
                    break
                }
                if (ret.ix > 0) break
            }
        return ret
    }

    //ピース選択
    fun selectPiece(layout: ConstraintLayout, selPiece: PiecePara) {
        //選択ピース以外は通常表示
        //Layer normal
        val gd = GradientDrawable()
        gd.setStroke(
            Tools.convertDp2Px(1f, layout.context).toInt(),
            layout.context.getThemeColor(R.attr.colorButtonNormal)
        )
        val ld = LayerDrawable(arrayOf<Drawable>(gd))
        ld.setLayerInset(0, 0, 0, 0, 0)
        //Layer odd
        val gdOdd = GradientDrawable()
        gdOdd.setStroke(
            Tools.convertDp2Px(1f, layout.context).toInt(),
            layout.context.getThemeColor(R.attr.colorButtonNormal)
        )
        gdOdd.setColor(oddColor)
        val ldOdd = LayerDrawable(arrayOf<Drawable>(gdOdd))
        ldOdd.setLayerInset(0, 0, 0, 0, 0)
        //Layer even
        val gdEven = GradientDrawable()
        gdEven.setStroke(
            Tools.convertDp2Px(1f, layout.context).toInt(),
            layout.context.getThemeColor(R.attr.colorButtonNormal)
        )
        gdEven.setColor(evenColor)
        val ldEven = LayerDrawable(arrayOf<Drawable>(gdEven))
        ldEven.setLayerInset(0, 0, 0, 0, 0)
        //Layer select
        val gdSel = GradientDrawable()
        gdSel.setStroke(
            Tools.convertDp2Px(1f, layout.context).toInt(),
            //Color.RED
            //Color.BLUE
            layout.context.getThemeColor(R.attr.colorButtonNormal)
        )
        gdSel.setColor(layout.context.getThemeColor(R.attr.colorButtonNormal))
        val ldSel = LayerDrawable(arrayOf<Drawable>(gdSel))
        ldSel.setLayerInset(0, 0, 0, 0, 0)
        //テーブル
        for (v in 0 until mAnswers) {
            for (v2 in 0 until mItems) {
                val tv = layout.findViewById<TextView>(tpPara[v][v2].id)
                if (selPiece.table && v == selPiece.iy && v2 == selPiece.ix)
                    tv.background = ldSel
                else
                    tv.background = if (v % 2 == 1) ldOdd else ldEven
            }
        }
        //持ち札
        for (v in 0 until mCardRows) {
            for (v2 in 0 until mItems) {
                val tv = layout.findViewById<TextView>(cpPara[v][v2].id)
                if (!selPiece.table && v == selPiece.iy && v2 == selPiece.ix)
                    tv.background = ldSel
                else
                    tv.background = ld
            }
        }
    }

    //ピース選択解除
    fun deselectPiece(layout: ConstraintLayout) {
        //Layer normal
        val gd = GradientDrawable()
        gd.setStroke(
            Tools.convertDp2Px(1f, layout.context).toInt(),
            layout.context.getThemeColor(R.attr.colorButtonNormal)
        )
        val ld = LayerDrawable(arrayOf<Drawable>(gd))
        ld.setLayerInset(0, 0, 0, 0, 0)
        //Layer odd
        val gdOdd = GradientDrawable()
        gdOdd.setStroke(
            Tools.convertDp2Px(1f, layout.context).toInt(),
            layout.context.getThemeColor(R.attr.colorButtonNormal)
        )
        gdOdd.setColor(oddColor)
        val ldOdd = LayerDrawable(arrayOf<Drawable>(gdOdd))
        ldOdd.setLayerInset(0, 0, 0, 0, 0)
        //Layer even
        val gdEven = GradientDrawable()
        gdEven.setStroke(
            Tools.convertDp2Px(1f, layout.context).toInt(),
            layout.context.getThemeColor(R.attr.colorButtonNormal)
        )
        gdEven.setColor(evenColor)
        val ldEven = LayerDrawable(arrayOf<Drawable>(gdEven))
        ldEven.setLayerInset(0, 0, 0, 0, 0)
        //テーブル
        for (v in 0 until mAnswers) {
            for (v2 in 0 until mItems) {
                val tv = layout.findViewById<TextView>(tpPara[v][v2].id)
                tv.background = if (v % 2 == 1) ldOdd else ldEven
            }
        }
        //持ち札
        for (v in 0 until mCardRows) {
            for (v2 in 0 until mItems) {
                val tv = layout.findViewById<TextView>(cpPara[v][v2].id)
                tv.background = ld
            }
        }
    }


}
