package io.github.jae02546.test_ekiblock

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.gms.ads.AdSize


object MainLayout {

    //レイアウトパラメータ
    var mPara: MutableList<MutableList<ItemPara>> = mutableListOf() //main
    var sPara: MutableList<MutableList<ItemPara>> = mutableListOf() //score
    var qPara: MutableList<MutableList<ItemPara>> = mutableListOf() //question
    var qiPara: MutableList<MutableList<ItemPara>> = mutableListOf() //questionItem
    var aPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answer
    var aiPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answerItem
    var pPara: MutableList<MutableList<ItemPara>> = mutableListOf() //piece
    var piPara: MutableList<MutableList<ItemPara>> = mutableListOf() //pieceItem
    var nPara: MutableList<MutableList<ItemPara>> = mutableListOf() //new game
    var adViewPara: MutableList<MutableList<ItemPara>> = mutableListOf() //adView

    //横item数
    private const val numItems = 8 //item幅計算時の分割数となる

    //問題縦item数（高さに使用）
    private const val numQuestion = 2

    //回答縦item数
    private const val numAnswers = 5

    //持ち札縦item数
    private const val numPieces = 3

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
        aPara = mutableListOf()
        aiPara = mutableListOf()
        pPara = mutableListOf()
        piPara = mutableListOf()
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
                    iWidth * numQuestion
                }
                2 -> { //回答
                    iWidth * numAnswers
                }
                3 -> { //持ち札
                    iWidth * numPieces
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
                    EnumViewType.ConstraintLayout, 0f, 0f, 0,
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
        val bdScore = GradientDrawable()
        bdScore.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdScore.setColor(context.getThemeColor(R.attr.colorButtonNormal))
        val ldScore = LayerDrawable(arrayOf<Drawable>(bdScore))
        ldScore.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldScore,
                    EnumViewType.TextView, 0f, 0f, 0,
                    v.toString() + v2.toString()
                )
            }
            sPara += foo
        }
        var sLayout = mLayout.getViewById(mPara[0][0].id) as ConstraintLayout
        sLayout = getConstraintLayout(sLayout, sPara)

        //question
        val bdQuestion = GradientDrawable()
        bdQuestion.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdQuestion.setColor(context.getThemeColor(R.attr.colorButtonNormal))
        val ldQuestion = LayerDrawable(arrayOf<Drawable>(bdQuestion))
        ldQuestion.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldQuestion,
                    EnumViewType.ConstraintLayout, 0f, 0f, 0,
                    ""
                )
            }
            qPara += foo
        }
        var qLayout = mLayout.getViewById(mPara[1][0].id) as ConstraintLayout
        qLayout = getConstraintLayout(qLayout, qPara)

        //question item
        val qic = context.getThemeColor(R.attr.editTextColor)
        val vWeight: MutableList<Float> = mutableListOf(0.5f, 1.0f, 0.5f, 0.5f)
        val numQuestionItems = 4
        for (v in 0 until numQuestionItems) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.TextView, vWeight[v], 0f, if (v != 1) 0 else qic,
                    v.toString() + v2.toString() //""
                )
            }
            qiPara += vPara
        }
        var qiLayout = qLayout.getViewById(qPara[0][0].id) as ConstraintLayout
        qiLayout = getConstraintLayout(qiLayout, qiPara)

        //answer
        val bdAnswer = GradientDrawable()
        bdAnswer.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdAnswer.setColor(context.getThemeColor(R.attr.colorSecondaryVariant))
        val ldAnswer = LayerDrawable(arrayOf<Drawable>(bdAnswer))
        ldAnswer.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldAnswer,
                    EnumViewType.ConstraintLayout, 0f, 0f, 0,
                    ""
                )
            }
            aPara += foo
        }
        var aLayout = mLayout.getViewById(mPara[2][0].id) as ConstraintLayout
        aLayout = getConstraintLayout(aLayout, aPara)

        //answer item
        val bdAnswerItem = GradientDrawable()
        bdAnswerItem.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        bdAnswerItem.setColor(context.getThemeColor(R.attr.colorOnPrimary)) //これだ
        val ldAnswerItem = LayerDrawable(arrayOf<Drawable>(bdAnswerItem))
        ldAnswerItem.setLayerInset(0, 0, 0, 0, 0)
        val aic = context.getThemeColor(R.attr.editTextColor)
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
                    ldAnswerItem,
                    EnumViewType.TextView, 0f, 0f, aic,
                    v.toString() + v2.toString() //""
                )
            }
            aiPara += vPara
        }
        var aiLayout = aLayout.getViewById(aPara[0][0].id) as ConstraintLayout
        aiLayout = getConstraintLayout(aiLayout, aiPara)

        //piece
        val bdPiece = GradientDrawable()
        bdPiece.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdPiece.setColor(context.getThemeColor(R.attr.colorPrimaryVariant))
        val ldPiece = LayerDrawable(arrayOf<Drawable>(bdPiece))
        ldPiece.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldPiece,
                    EnumViewType.ConstraintLayout, 0f, 0f, 0,
                    ""
                )
            }
            pPara += foo
        }
        var pLayout = mLayout.getViewById(mPara[3][0].id) as ConstraintLayout
        pLayout = getConstraintLayout(pLayout, pPara)

        //piece item
        val bdPieceItem = GradientDrawable()
        bdPieceItem.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        bdPieceItem.setColor(context.getThemeColor(R.attr.colorOnPrimary)) //これだ
        val ldPieceItem = LayerDrawable(arrayOf<Drawable>(bdPieceItem))
        ldPieceItem.setLayerInset(0, 0, 0, 0, 0)
        val pic = context.getThemeColor(R.attr.editTextColor)
        for (v in 0 until numPieces) {
            val vPara: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0 until numItems) {
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != numItems - 1) viewMargin else viewMargin * 2
                val tMargin = if (v != 0) viewMargin else viewMargin * 2
                val bMargin = if (v != numPieces - 1) viewMargin else viewMargin * 2
                vPara += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, tMargin, bMargin,
                    ldPieceItem,
                    EnumViewType.TextView, 0f, 0f, pic,
                    v.toString() + v2.toString() //""
                )
            }
            piPara += vPara
        }
        var piLayout = pLayout.getViewById(pPara[0][0].id) as ConstraintLayout
        piLayout = getConstraintLayout(piLayout, piPara)

        //newGame
        val bdNewGame = GradientDrawable()
        bdNewGame.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdNewGame.setColor(context.getThemeColor(R.attr.colorButtonNormal))
        val ldNewGame = LayerDrawable(arrayOf<Drawable>(bdNewGame))
        ldNewGame.setLayerInset(0, 0, 0, 0, 0)
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldNewGame,
                    EnumViewType.TextView, 0f, 0f, 0,
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
                    EnumViewType.AdView, 0f, 0f, 0,
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
                        child.gravity = Gravity.CENTER
                        child.typeface = Typeface.MONOSPACE;
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
    ) {
        //スコア表示
        val sRec = RoomMain.getScoreRecord(layout.context, pNo)
        val sTv = layout.findViewById<TextView>(sPara[0][0].id)
        if (sRec != null) {
            val foo = "プレイ数:" + sRec.pCount.toString() + "\nコンプ数:" + sRec.cCount.toString()
            sTv.text = foo
        } else {
            val foo = "プレイ数:0\nコンプ数:0"
            sTv.text = foo
        }
        //問い表示
        val lRec = RoomMain.getLastStateRecord(layout.context, pNo)
        //ラスト状態が無い場合は問いを作成
        if (lRec == null) {
            val qNoList = RoomMain.getQuestionNoList(layout.context)
            if (qNoList.count() > 0) {
                //qListをシャッフルして先頭の問いから新しい問題を作成
                qNoList.shuffle()
                val qRec = RoomMain.getQuestionRecord(layout.context, qNoList[0])
                if (qRec != null) {
                    val qiList: MutableList<QuestionItemTbl> = mutableListOf()
                    for (v in qRec.qiList)
                        qiList += v
                    qiList.shuffle()
                    //先頭から5駅分を問題とする、ただし8文字以下の駅を対象とする
                    val foo: MutableList<Int> = mutableListOf()
                    for (v in qiList) {
                        if (v.name.length <= numItems)
                            foo += v.iNo
                        if (foo.count() >= numAnswers)
                            break
                    }







                }
            }
        }
        if (lRec != null) {
            val qRec = RoomMain.getQuestionRecord(layout.context, lRec.qNo)
            if (qRec != null) {
                val kana = layout.findViewById<TextView>(qiPara[0][0].id)
                kana.text = qRec.kana
                val name = layout.findViewById<TextView>(qiPara[1][0].id)
                name.text = qRec.name
                val english = layout.findViewById<TextView>(qiPara[2][0].id)
                english.text = qRec.english
                val info = layout.findViewById<TextView>(qiPara[3][0].id)
                info.text = qRec.info3
            } else {
                //ここにはこないはず
                for (v in qiPara) {
                    for (v2 in v) {
                        val foo = layout.findViewById<TextView>(v2.id)
                        foo.text = ""
                    }
                }
            }
        } else {
            //ここにはこないはず
            for (v in qiPara) {
                for (v2 in v) {
                    val foo = layout.findViewById<TextView>(v2.id)
                    foo.text = ""
                }
            }
        }


//
//        val c1Layout = layout.findViewById<ConstraintLayout>(cPara[1][0].id)
//        val c2Layout = layout.findViewById<ConstraintLayout>(cPara[2][0].id)
//
//        //問いデータ表示
//        for (v in 0 until numDigits) {
//            for (v2 in 0 until numDigits) {
//                var offsetV = 0
//                var offsetV2 = 0
//                when (numDigits) {
//                    2 -> {
//                        offsetV = 3
//                        offsetV2 = 5
//                    }
//                    4 -> {
//                        offsetV = 2
//                        offsetV2 = 6
//                    }
//                    6 -> {
//                        offsetV = 1
//                        offsetV2 = 7
//                    }
//                    8 -> {
//                        offsetV = 0
//                        offsetV2 = 8
//                    }
//                    else -> {
//                        //何もしない
//                    }
//                }
//                val tv = c1Layout.getViewById(c1Para[offsetV + v][offsetV2 - v2].id) as TextView
//                tv.text = if (question[v] and 2.0.pow(v2).toInt() == 0) "0" else "1"
//                //dec,hex
//                val foo = question[v].toString().padStart(3, ' ') + "\n " + question[v].toString(16)
//                    .padStart(2, '0')
//                val dh = c1Layout.getViewById(c1Para[offsetV + v][9].id) as TextView
////                dh.gravity = Gravity.CENTER
////                dh.typeface = Typeface.MONOSPACE;
////                dh.textSize = 12F
//                dh.text = foo
//
//            }
//        }
//        //選択ライン表示
//        for (v in 0..7) {
//            for (v2 in 1..8) {
//                val tv = c1Layout.getViewById(c1Para[v][v2].id) as TextView
//                if (v == selLine)
//                    tv.background = c1Layout.context.getDrawable(R.drawable.border_red)
//                else
//                    tv.background = c1Layout.context.getDrawable(R.drawable.border)
//            }
//        }
//
//        //total表示
//        var total = 0
//        for (v in question) {
//            total += v
//            total = total and (2.0.pow(numDigits).toInt() - 1)
//        }
//
//        for (v in 0 until numDigits) {
//            var offset = 0
//            when (numDigits) {
//                2 -> {
//                    offset = 5
//                }
//                4 -> {
//                    offset = 6
//                }
//                6 -> {
//                    offset = 7
//                }
//                8 -> {
//                    offset = 8
//                }
//                else -> {
//                    //何もしない
//                }
//            }
//            val tv = c2Layout.getViewById(c2Para[0][offset - v].id) as TextView
//            tv.text = if (total and 2.0.pow(v).toInt() == 0) "0" else "1"
//        }
//        val foo = total.toString().padStart(3, ' ') + "\n " + total.toString(16).padStart(2, '0')
//        val dh = c2Layout.getViewById(c2Para[0][9].id) as TextView
//        dh.text = foo

    }

}