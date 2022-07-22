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
    var aPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answer
    var aiPara: MutableList<MutableList<ItemPara>> = mutableListOf() //answerItem
    var pPara: MutableList<MutableList<ItemPara>> = mutableListOf() //piece
    var piPara: MutableList<MutableList<ItemPara>> = mutableListOf() //pieceItem
    var nPara: MutableList<MutableList<ItemPara>> = mutableListOf() //new game
    var adViewPara: MutableList<MutableList<ItemPara>> = mutableListOf() //adView

    //横item数
    private const val numItems = 8 //item幅計算時の分割数となる

    //問題縦item数
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


    fun makeLayout(context: Context, screenSize: MutableList<Int>): ConstraintLayout {
        //パラメータ初期化
        mPara = mutableListOf()
        sPara = mutableListOf()
        qPara = mutableListOf()
        aPara = mutableListOf()
        aiPara = mutableListOf()
        pPara = mutableListOf()
        piPara = mutableListOf()
        nPara = mutableListOf()
        adViewPara = mutableListOf()

        //薄い枠線 ダークテーマ可 colorButtonNormal
        val bdNormal = GradientDrawable()
        bdNormal.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        //bdNormal.setColor(context.getThemeColor(R.attr.colorButtonNormal))
        val ldNormal = LayerDrawable(arrayOf<Drawable>(bdNormal))
        ldNormal.setLayerInset(0, 0, 0, 0, 0)

        //item
        val bdItem = GradientDrawable()
        bdItem.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        //塗りつぶしはコメントを外す
        bdItem.setColor(context.getThemeColor(R.attr.colorOnPrimary)) //これだ
        val ldItem = LayerDrawable(arrayOf<Drawable>(bdItem))
        ldItem.setLayerInset(0, 0, 0, 0, 0)

        //回答背景
        val bdAnswer = GradientDrawable()
        bdAnswer.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorSecondaryVariant)
        )
        //塗りつぶしはコメントを外す
        bdAnswer.setColor(context.getThemeColor(R.attr.colorSecondaryVariant))
        val ldAnswer = LayerDrawable(arrayOf<Drawable>(bdAnswer))
        ldAnswer.setLayerInset(0, 0, 0, 0, 0)

        //持ち手背景
        val bdPiece = GradientDrawable()
        bdPiece.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorPrimaryVariant)
        )
        //塗りつぶしはコメントを外す
        bdPiece.setColor(context.getThemeColor(R.attr.colorPrimaryVariant))
        val ldPiece = LayerDrawable(arrayOf<Drawable>(bdPiece))
        ldPiece.setLayerInset(0, 0, 0, 0, 0)

        //赤枠
        val bdRed = GradientDrawable()
        bdRed.setStroke(
            Tools.convertDp2Px(0f, context).toInt(),
            Color.RED
        )
        //塗りつぶしはコメントを外す
        //bdRed.setColor(Color.argb(0xff, 255, 0, 0))
        val ldRed = LayerDrawable(arrayOf<Drawable>(bdRed))
        ldRed.setLayerInset(0, 0, 0, 0, 0)


        //item基本幅
        val iWidth = screenSize[0] / numItems

        //main[score][question][answer][piece][newGame][adView]
        for (v in 0..5) {
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
                    0, 0, 0, 0,
                    LayerDrawable(arrayOf<Drawable>()),
                    EnumViewType.ConstraintLayout, 0f, 0,
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
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    viewMargin, viewMargin, viewMargin, viewMargin,
                    ldNormal,
                    EnumViewType.TextView, 0f, 0,
                    v.toString() + v2.toString()
                )
            }
            sPara += foo
        }
        var sLayout = mLayout.getViewById(mPara[0][0].id) as ConstraintLayout
        sLayout = getConstraintLayout(sLayout, sPara)

        //question
        for (v in 0..3) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    viewMargin, viewMargin, viewMargin, viewMargin,
                    ldNormal,
                    EnumViewType.TextView, 0f, 0,
                    v.toString() + v2.toString()
                )
            }
            qPara += foo
        }
        var qLayout = mLayout.getViewById(mPara[1][0].id) as ConstraintLayout
        qLayout = getConstraintLayout(qLayout, qPara)

        //answer
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldAnswer,
                    EnumViewType.ConstraintLayout, 0f, 0,
                    ""
                )
            }
            aPara += foo
        }
        var aLayout = mLayout.getViewById(mPara[2][0].id) as ConstraintLayout
        aLayout = getConstraintLayout(aLayout, aPara)

        //answer item
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
                    ldItem,
                    EnumViewType.TextView, 0f, aic,
                    v.toString() + v2.toString() //""
                )
            }
            aiPara += vPara
        }
        var aiLayout = aLayout.getViewById(aPara[0][0].id) as ConstraintLayout
        aiLayout = getConstraintLayout(aiLayout, aiPara)

        //piece
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    ldPiece,
                    EnumViewType.ConstraintLayout, 0f, 0,
                    ""
                )
            }
            pPara += foo
        }
        var pLayout = mLayout.getViewById(mPara[3][0].id) as ConstraintLayout
        pLayout = getConstraintLayout(pLayout, pPara)

        //piece item
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
                    ldItem,
                    EnumViewType.TextView, 0f, pic,
                    v.toString() + v2.toString() //""
                )
            }
            piPara += vPara
        }
        var piLayout = pLayout.getViewById(pPara[0][0].id) as ConstraintLayout
        piLayout = getConstraintLayout(piLayout, piPara)

        //newGame
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    viewMargin, viewMargin, viewMargin, viewMargin,
                    ldNormal,
                    EnumViewType.TextView, 0f, 0,
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
                    EnumViewType.AdView, 0f, 0,
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


//                        if (v2.border)
//                            child.background = layout.context.getDrawable(R.drawable.border)


//                        if (v2.border) {
//                            val borderDrawable = GradientDrawable()
//                            borderDrawable.setStroke(
//                                Tools.convertDp2Px(5f, layout.context).toInt(),
//                                Color.RED
//                            )
//                            borderDrawable.setColor(Color.GREEN)
//                            val layerDrawable = LayerDrawable(arrayOf<Drawable>(borderDrawable))
//                            layerDrawable.setLayerInset(0, 0, 0, 0, 0)
//                            child.background = layerDrawable
//                        }
//
//
//                        if (v2.backgroundColor)
//                            child.setBackgroundColor(v2.color)
                        layout.addView(child)


                    }
                    EnumViewType.TextView -> {
                        val child = TextView(layout.context)
                        child.background = v2.layerDrawable

                        child.gravity = Gravity.CENTER
                        child.typeface = Typeface.MONOSPACE;
                        if (v2.fontSize != 0f)
                            child.textSize = v2.fontSize
                        if (v2.fontColor != 0)
                            child.setTextColor(v2.fontColor)
                        child.id = v2.id

//                        if (v2.border)
//                            child.background = layout.context.getDrawable(R.drawable.border)
//                        if (v2.backgroundColor)
//                            child.setBackgroundColor(v2.color)

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


//    fun showLayout(
//        layout: ConstraintLayout,
//        question: MutableList<Int>,
//        numDigits: Int,
//        selLine: Int,
//    ) {
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
//
//    }

}