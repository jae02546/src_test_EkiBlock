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
    var cItemPara: MutableList<MutableList<MutableList<MutableList<ItemPara>>>> = mutableListOf()

    var pPara: MutableList<MutableList<ItemPara>> = mutableListOf() //piece

    var nPara: MutableList<MutableList<ItemPara>> = mutableListOf() //new game
    var b0Para: MutableList<MutableList<ItemPara>> = mutableListOf()
    var b1Para: MutableList<MutableList<ItemPara>> = mutableListOf()
    var adViewPara: MutableList<MutableList<ItemPara>> = mutableListOf() //adView

    //Viewマージン それぞれのViewがこのマージンを取るので実際の間隔は倍となる
    private const val viewMargin = 3 //5

    //adView高さ
    private const val adViewHeight = 50

    //textView fontSize（問い部分以外）
    private const val tvFontSize = 12f

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
//        var backgroundColor: Boolean = false,
//        var color: Int = 0,
//        var border: Boolean = false,
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

    fun makeLayout(context: Context, screenSize: MutableList<Int>): ConstraintLayout {
        //パラメータ初期化
        mPara = mutableListOf()
        sPara = mutableListOf()
        qPara = mutableListOf()
        aPara = mutableListOf()
        pPara = mutableListOf()
        nPara = mutableListOf()
        adViewPara = mutableListOf()

        cItemPara = mutableListOf()
        b0Para = mutableListOf()
        b1Para = mutableListOf()

        //ノーマルレイヤ
        val borderDrawableNormal = GradientDrawable()
        borderDrawableNormal.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        val layerDrawableNormal = LayerDrawable(arrayOf<Drawable>(borderDrawableNormal))
        layerDrawableNormal.setLayerInset(0, 0, 0, 0, 0)
        //赤レイヤ
        val borderDrawableRed = GradientDrawable()
        borderDrawableRed.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            Color.RED
        )
        borderDrawableRed.setColor(Color.argb(127, 255, 0, 0))
        val layerDrawableRed = LayerDrawable(arrayOf<Drawable>(borderDrawableRed))
        layerDrawableRed.setLayerInset(0, 0, 0, 0, 0)
        //黒レイヤ
        val borderDrawableItem = GradientDrawable()
        borderDrawableItem.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            Color.BLACK
        )
        borderDrawableItem.setColor(Color.argb(127, 0, 255, 0))
        val layerDrawableItem = LayerDrawable(arrayOf<Drawable>(borderDrawableItem))
        layerDrawableItem.setLayerInset(0, 0, 0, 0, 0)

        //item基本幅
        val iWidth = screenSize[0] / 8

        //main[score][question][answer][piece][newGame][adView]
        for (v in 0..5) {
            val height = when (v) {
                1 -> {
                    //問題3行
                    iWidth * 2
                }
                2 -> {
                    //回答5行
                    iWidth * 5
                }
                3 -> {
                    //持ち札3行
                    iWidth * 3
                }
                5 -> {
                    //adView
                    Tools.convertDp2Px(adViewHeight.toFloat(), context).toInt()
                }
                else -> {
                    //score newGame
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
                    layerDrawableNormal,
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
                    layerDrawableNormal,
                    EnumViewType.TextView, 0f, 0,
                    v.toString() + v2.toString()
                )
            }
            qPara += foo
        }
        var qLayout = mLayout.getViewById(mPara[1][0].id) as ConstraintLayout
        qLayout = getConstraintLayout(qLayout, qPara)




        //center
        //[0]路線1 [1]路線2 [2]路線3 [3]路線4
        val bc: MutableList<MutableList<Int>> = mutableListOf(
            mutableListOf(Color.RED, Color.BLUE),
            mutableListOf(Color.CYAN, Color.GREEN)
        )
        for (v in 0..1) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..1) {
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != 1) viewMargin else viewMargin * 2
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, viewMargin, viewMargin,
                    layerDrawableRed,
//                    false, bc[v][v2],
//                    true,
                    EnumViewType.ConstraintLayout, 0f, 0,
                    ""
                )
            }
            aPara += foo
        }
        var cLayout = mLayout.getViewById(mPara[2][0].id) as ConstraintLayout
        cLayout = getConstraintLayout(cLayout, aPara)

        //center item
        val fc = context.getThemeColor(R.attr.editTextColor)
        for (v in aPara) {
            val v2Para: MutableList<MutableList<MutableList<ItemPara>>> = mutableListOf()
            for (v2 in v) {
                val viPara: MutableList<MutableList<ItemPara>> = mutableListOf()
                for (vi in 0..3) {
                    val vi2Para: MutableList<ItemPara> = mutableListOf()
                    for (vi2 in 0..3) {
                        val lMargin = if (vi2 != 0) viewMargin else viewMargin * 2
                        val rMargin = if (vi2 != 3) viewMargin else viewMargin * 2
                        val tMargin = if (vi != 0) viewMargin else viewMargin * 2
                        val bMargin = if (vi != 3) viewMargin else viewMargin * 2
                        vi2Para += ItemPara(
                            View.generateViewId(),
                            0, 0,
                            lMargin, rMargin, tMargin, bMargin,
                            layerDrawableItem,
//                            false, 0,
//                            true,
                            EnumViewType.TextView, 0f, fc,
//                            v.toString() + v2.toString() + vi.toString() + vi2.toString() //""
                            vi.toString() + vi2.toString() //""
                        )
                    }
                    viPara += vi2Para
                }
                var ciLayout = cLayout.getViewById(v2.id) as ConstraintLayout
                ciLayout = getConstraintLayout(ciLayout, viPara)
                v2Para += viPara
            }
            cItemPara += v2Para
        }

        //piece
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != 1) viewMargin else viewMargin * 2
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, viewMargin, viewMargin,
                    layerDrawableRed,
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
        for (v in pPara) {
            val v2Para: MutableList<MutableList<MutableList<ItemPara>>> = mutableListOf()
            for (v2 in v) {
                val viPara: MutableList<MutableList<ItemPara>> = mutableListOf()
                for (vi in 0..3) {
                    val vi2Para: MutableList<ItemPara> = mutableListOf()
                    for (vi2 in 0..3) {
                        val lMargin = if (vi2 != 0) viewMargin else viewMargin * 2
                        val rMargin = if (vi2 != 3) viewMargin else viewMargin * 2
                        val tMargin = if (vi != 0) viewMargin else viewMargin * 2
                        val bMargin = if (vi != 3) viewMargin else viewMargin * 2
                        vi2Para += ItemPara(
                            View.generateViewId(),
                            0, 0,
                            lMargin, rMargin, tMargin, bMargin,
                            layerDrawableItem,
//                            false, 0,
//                            true,
                            EnumViewType.TextView, 0f, fc,
//                            v.toString() + v2.toString() + vi.toString() + vi2.toString() //""
                            vi.toString() + vi2.toString() //""
                        )
                    }
                    viPara += vi2Para
                }
                var ciLayout = cLayout.getViewById(v2.id) as ConstraintLayout
                ciLayout = getConstraintLayout(ciLayout, viPara)
                v2Para += viPara
            }
            cItemPara += v2Para
        }



        //newGame
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    viewMargin, viewMargin, viewMargin, viewMargin,
                    layerDrawableNormal,
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


}