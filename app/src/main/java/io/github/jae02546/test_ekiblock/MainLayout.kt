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
import com.google.android.gms.ads.AdSize


object MainLayout {

    //レイアウトパラメータ
    var mPara: MutableList<MutableList<ItemPara>> = mutableListOf() //main
    var sPara: MutableList<MutableList<ItemPara>> = mutableListOf() //score
    var qPara: MutableList<MutableList<ItemPara>> = mutableListOf() //question
    var qiPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionItem
    var qhPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionHint
    var qnPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionName
    var aPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answer
    var aiPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answerItem
    var atPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answerTitle
    var apPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answerPiece
    var cPara: MutableList<MutableList<ItemPara>> = mutableListOf() //card
    var ciPara: MutableList<MutableList<ItemPara>> = mutableListOf() //cardItem
    var ctPara: MutableList<MutableList<ItemPara>> = mutableListOf() //cardTitle
    var cpPara: MutableList<MutableList<ItemPara>> = mutableListOf() //cardPiece
    var nPara: MutableList<MutableList<ItemPara>> = mutableListOf() //new game
    var adViewPara: MutableList<MutableList<ItemPara>> = mutableListOf() //adView

    //横item数
//    private const val numItems = 8 //item幅計算時の分割数となる
    private const val numItems = 9 //item幅計算時の分割数となる

    //問題縦item数（高さに使用）
    private const val numQuestion = 2

    //回答縦item数
    private const val numAnswers = 5

    //持ち札縦item数
    private const val numCards = 3

    //Viewマージン それぞれのViewがこのマージンを取るので実際の間隔は倍となる
    private const val viewMargin = 10 //dp

    //adView高さ
    private const val adViewHeight = 50 //dp


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
        sPara = mutableListOf()
        qPara = mutableListOf()
        qiPara = mutableListOf()
        qhPara = mutableListOf()
        qnPara = mutableListOf()
        aPara = mutableListOf()
        aiPara = mutableListOf()
        atPara = mutableListOf()
        apPara = mutableListOf()
        cPara = mutableListOf()
        ciPara = mutableListOf()
        ctPara = mutableListOf()
        cpPara = mutableListOf()
        nPara = mutableListOf()
        adViewPara = mutableListOf()

        //item基本幅
        val iWidth = screenSize[0] / numItems

        //main[score][question][answer][piece][newGame][adView]
        val numMains = 6
        val mMargin = 2
        val mlMargin: MutableList<Int> = mutableListOf(
            mMargin * 2,
            mMargin * 2,
            mMargin * 2,
            mMargin * 2,
            mMargin * 2,
            0
        )
        val mrMargin: MutableList<Int> = mutableListOf(
            mMargin * 2,
            mMargin * 2,
            mMargin * 2,
            mMargin * 2,
            mMargin * 2,
            0
        )
        val mtMargin: MutableList<Int> = mutableListOf(
            mMargin * 2,
            mMargin,
            mMargin,
            mMargin,
            mMargin,
            0
        )
        val mbMargin: MutableList<Int> = mutableListOf(
            mMargin,
            mMargin,
            mMargin,
            mMargin,
            mMargin * 2,
            0
        )
        for (v in 0 until numMains) {
            val height = when (v) {
                1 -> { //問題
                    iWidth * numQuestion - iWidth / 2 //- iWidth / 2 は調整
                }
                2 -> { //回答
                    iWidth * numAnswers + iWidth / 2 //+ iWidth / 2 はタイトル
                }
                3 -> { //持ち札
                    iWidth * numCards + iWidth / 2 //+ iWidth / 2 はタイトル
                }
                5 -> { //adView
                    Tools.convertDp2Px(adViewHeight.toFloat(), context).toInt()
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
                    v.toString() + v2.toString()
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

        //question item
        for (v in 0..0) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..2) {
                vPara += ItemPara(
                    View.generateViewId(),
                    if (v2 != 1) iWidth else 0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    v.toString() + v2.toString() //""
                )
            }
            qiPara += vPara
        }
        var qiLayout = qLayout.getViewById(qPara[0][0].id) as ConstraintLayout
        qiLayout = getConstraintLayout(qiLayout, qiPara)

        //question hint
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
                    v.toString() + v2.toString() //""
                )
            }
            qhPara += vPara
        }
        var qhLayout = qiLayout.getViewById(qiPara[0][0].id) as ConstraintLayout
        qhLayout = getConstraintLayout(qhLayout, qhPara)

        //question name
        val qnc = context.getThemeColor(R.attr.editTextColor)
        val vWeight: MutableList<Float> = mutableListOf(1.0f, 1.0f, 1.0f, 1.0f)
        val fSize: MutableList<Float> = mutableListOf(10f, 0f, 10f, 10f)
        val gravity: MutableList<Int> =
            mutableListOf(
                Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM,
                Gravity.CENTER,
                Gravity.CENTER_HORIZONTAL + Gravity.TOP,
                Gravity.CENTER
            )
        val numName = 4
        for (v in 0 until numName) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.TextView,
                    vWeight[v],
                    fSize[v],
                    if (v != 1) 0 else qnc,
                    gravity[v],
                    v.toString() + v2.toString() //""
                )
            }
            qnPara += vPara
        }
        var qnLayout = qiLayout.getViewById(qiPara[0][1].id) as ConstraintLayout
        qnLayout = getConstraintLayout(qnLayout, qnPara)

        //answer
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
            aPara += foo
        }
        var aLayout = mLayout.getViewById(mPara[2][0].id) as ConstraintLayout
        aLayout = getConstraintLayout(aLayout, aPara)

        //answer item
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
            aiPara += foo
        }
        var aiLayout = aLayout.getViewById(aPara[0][0].id) as ConstraintLayout
        aiLayout = getConstraintLayout(aiLayout, aiPara)

        //answer title
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.TextView, 0f, 10f, 0, Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM,
                    "テーブル"
                )
            }
            atPara += foo
        }
        var atLayout = aiLayout.getViewById(aiPara[0][0].id) as ConstraintLayout
        atLayout = getConstraintLayout(atLayout, atPara)

        //answer piece
        val gdAnswerPiece = GradientDrawable()
        gdAnswerPiece.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        gdAnswerPiece.setColor(context.getThemeColor(R.attr.colorOnPrimary)) //これだ
        val ldAnswerPiece = LayerDrawable(arrayOf<Drawable>(gdAnswerPiece))
        ldAnswerPiece.setLayerInset(0, 0, 0, 0, 0)
        val apc = context.getThemeColor(R.attr.editTextColor)
        for (v in 0 until numAnswers) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0 until numItems) {
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != numItems - 1) viewMargin else viewMargin * 2
                val tMargin = if (v != 0) viewMargin else viewMargin * 2
                val bMargin = if (v != numAnswers - 1) viewMargin else viewMargin * 2
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, tMargin, bMargin,
                    ldAnswerPiece,
                    EnumViewType.TextView, 0f, 0f, apc, Gravity.CENTER,
                    v.toString() + v2.toString() //""
                )
            }
            apPara += vPara
        }
        var apLayout = aiLayout.getViewById(aiPara[1][0].id) as ConstraintLayout
        apLayout = getConstraintLayout(apLayout, apPara)

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
                    EnumViewType.TextView, 0f, 10f, 0, Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM,
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
        for (v in 0 until numCards) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0 until numItems) {
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != numItems - 1) viewMargin else viewMargin * 2
                val tMargin = if (v != 0) viewMargin else viewMargin * 2
                val bMargin = if (v != numCards - 1) viewMargin else viewMargin * 2
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, tMargin, bMargin,
                    ldCardPiece,
                    EnumViewType.TextView, 0f, 0f, cpc, Gravity.CENTER,
                    v.toString() + v2.toString() //""
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
                    "new game"
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

    private fun getConstraintLayout(
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
        var lRec = RoomMain.getLastStateRecord(layout.context, pNo)
        //ラスト状態が無い場合も問いを作成
        if (lRec == null || newGame) {
            newGame(layout.context, pNo)
            //再読み込み
            lRec = RoomMain.getLastStateRecord(layout.context, pNo)
        }
        if (lRec != null) {
            val qRec = RoomMain.getQuestionRecord(layout.context, lRec.qNo)
            if (qRec != null) {
                val hint = layout.findViewById<TextView>(qhPara[0][0].id)
                val foo =
                    if (qRec.url != "") "<a href=" + (qRec.url) + ">" + "Hint" + "</a>" else ""
                hint.text = HtmlCompat.fromHtml(foo, HtmlCompat.FROM_HTML_MODE_COMPACT)
                hint.movementMethod = LinkMovementMethod.getInstance()
                val kana = layout.findViewById<TextView>(qnPara[0][0].id)
                kana.text = qRec.kana
                val name = layout.findViewById<TextView>(qnPara[1][0].id)
                name.text = qRec.name
                val english = layout.findViewById<TextView>(qnPara[2][0].id)
                english.text = qRec.english
                val info = layout.findViewById<TextView>(qnPara[3][0].id)
                //info.text = qRec.info3
                val bar = qRec.name + "の駅を" + lRec.cList.count() + "駅答えてください"
                info.text = bar
            } else {
                //ここにはこないはず
                for (v in qnPara) {
                    for (v2 in v) {
                        val foo = layout.findViewById<TextView>(v2.id)
                        foo.text = ""
                    }
                }
            }
        } else {
            //ここにはこないはず
            for (v in qnPara) {
                for (v2 in v) {
                    val foo = layout.findViewById<TextView>(v2.id)
                    foo.text = ""
                }
            }
        }
        //回答表示
        if (lRec != null) {
            for (v in 0 until numAnswers) {
                for (v2 in 0 until numItems) {
                    val atv = layout.findViewById<TextView>(apPara[v][v2].id)
                    if (lRec.aList.count() > v && lRec.aList[v].count() > v2) {
                        atv.text = lRec.aList[v][v2]
                    } else {
                        atv.text = ""
                    }
                }
            }
        }
        //持ち札表示
        if (lRec != null) {
            for (v in 0 until numCards) {
                for (v2 in 0 until numItems) {
                    val ptv = layout.findViewById<TextView>(cpPara[v][v2].id)
                    if (lRec.pList.count() > v && lRec.pList[v].count() > v2) {
                        ptv.text = lRec.pList[v][v2]
                    } else {
                        ptv.text = ""
                    }
                }
            }
        }


    }

    //新しい問題を作成
    private fun newGame(context: Context, pNo: Int) {
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
                    cNoList = newGameSelectSta(qRec)
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

    private fun newGameSelectSta(qRec: QuestionTbl): MutableList<Int> {
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


}
