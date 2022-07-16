package io.github.jae02546.test_ekiblock

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.gms.ads.AdSize
import kotlin.math.pow


object MainLayout {

    //レイアウトパラメータ
    var mPara: MutableList<MutableList<ItemPara>> = mutableListOf()
    var tPara: MutableList<MutableList<ItemPara>> = mutableListOf()
    var cPara: MutableList<MutableList<ItemPara>> = mutableListOf()
    var c0Para: MutableList<MutableList<ItemPara>> = mutableListOf()
    var c1Para: MutableList<MutableList<ItemPara>> = mutableListOf()
    var c2Para: MutableList<MutableList<ItemPara>> = mutableListOf()
    var bPara: MutableList<MutableList<ItemPara>> = mutableListOf()
    var b0Para: MutableList<MutableList<ItemPara>> = mutableListOf()
    var b1Para: MutableList<MutableList<ItemPara>> = mutableListOf()
    var adViewPara: MutableList<MutableList<ItemPara>> = mutableListOf()

    //Viewマージン それぞれのViewがこのマージンを取るので実際の間隔は倍となる
    private const val viewMargin = 5

    //center両端部分の幅
    private const val canterInfoWidth = 50

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
        var backgroundColor: Boolean = false,
        var color: Int = 0,
        var border: Boolean = false,
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

    fun showLayout(
        layout: ConstraintLayout,
        question: MutableList<Int>,
        numDigits: Int,
        selLine: Int,
    ) {
        val c1Layout = layout.findViewById<ConstraintLayout>(cPara[1][0].id)
        val c2Layout = layout.findViewById<ConstraintLayout>(cPara[2][0].id)

        //問いデータ表示
        for (v in 0 until numDigits) {
            for (v2 in 0 until numDigits) {
                var offsetV = 0
                var offsetV2 = 0
                when (numDigits) {
                    2 -> {
                        offsetV = 3
                        offsetV2 = 5
                    }
                    4 -> {
                        offsetV = 2
                        offsetV2 = 6
                    }
                    6 -> {
                        offsetV = 1
                        offsetV2 = 7
                    }
                    8 -> {
                        offsetV = 0
                        offsetV2 = 8
                    }
                    else -> {
                        //何もしない
                    }
                }
                val tv = c1Layout.getViewById(c1Para[offsetV + v][offsetV2 - v2].id) as TextView
                tv.text = if (question[v] and 2.0.pow(v2).toInt() == 0) "0" else "1"
                //dec,hex
                val foo = question[v].toString().padStart(3, ' ') + "\n " + question[v].toString(16)
                    .padStart(2, '0')
                val dh = c1Layout.getViewById(c1Para[offsetV + v][9].id) as TextView
//                dh.gravity = Gravity.CENTER
//                dh.typeface = Typeface.MONOSPACE;
//                dh.textSize = 12F
                dh.text = foo

            }
        }
        //選択ライン表示
        for (v in 0..7) {
            for (v2 in 1..8) {
                val tv = c1Layout.getViewById(c1Para[v][v2].id) as TextView
                if (v == selLine)
                    tv.background = c1Layout.context.getDrawable(R.drawable.border_red)
                else
                    tv.background = c1Layout.context.getDrawable(R.drawable.border)
            }
        }

        //total表示
        var total = 0
        for (v in question) {
            total += v
            total = total and (2.0.pow(numDigits).toInt() - 1)
        }

        for (v in 0 until numDigits) {
            var offset = 0
            when (numDigits) {
                2 -> {
                    offset = 5
                }
                4 -> {
                    offset = 6
                }
                6 -> {
                    offset = 7
                }
                8 -> {
                    offset = 8
                }
                else -> {
                    //何もしない
                }
            }
            val tv = c2Layout.getViewById(c2Para[0][offset - v].id) as TextView
            tv.text = if (total and 2.0.pow(v).toInt() == 0) "0" else "1"
        }
        val foo = total.toString().padStart(3, ' ') + "\n " + total.toString(16).padStart(2, '0')
        val dh = c2Layout.getViewById(c2Para[0][9].id) as TextView
        dh.text = foo

    }

    fun makeLayout(context: Context, screenSize: MutableList<Int>): ConstraintLayout {
        //パラメータ初期化
        mPara = mutableListOf()
        tPara = mutableListOf()
        cPara = mutableListOf()
        c0Para = mutableListOf()
        c1Para = mutableListOf()
        c2Para = mutableListOf()
        bPara = mutableListOf()
        b0Para = mutableListOf()
        b1Para = mutableListOf()
        adViewPara = mutableListOf()

        //centerアイテム基本幅
        val itemWidth =
            (screenSize[0] - Tools.convertDp2Px(canterInfoWidth.toFloat() * 2, context).toInt()) / 8

        //main[top][center][bottom][adView]
        for (v in 0..3) {
            val height = when (v) {
                1 -> {
                    //center
                    itemWidth * 10
                }
                3 -> {
                    //adView
                    Tools.convertDp2Px(adViewHeight.toFloat(), context).toInt()
                }
                else -> {
                    0
                }
            }
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, height,
                    0, 0, 0, 0,
                    false, 0,
                    false,
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

        //top
        for (v in 0..3) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    viewMargin, viewMargin, viewMargin, viewMargin,
                    false, 0,
                    true,
                    EnumViewType.TextView, 0f, 0,
                    v.toString() + v2.toString()
                )
            }
            tPara += foo
        }
        var tLayout = mLayout.getViewById(mPara[0][0].id) as ConstraintLayout
        tLayout = getConstraintLayout(tLayout, tPara)

        //center
        //[0]no bin dec/hex [1]question [2]total
        for (v in 0..2) {
            val height = if (v != 0 && v != 2) 0 else itemWidth
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, height,
                    0, 0, 0, 0,
                    false, 0,
                    false,
                    EnumViewType.ConstraintLayout, 0f, 0,
                    ""
                )
            }
            cPara += foo
        }
        var cLayout = mLayout.getViewById(mPara[1][0].id) as ConstraintLayout
        cLayout = getConstraintLayout(cLayout, cPara)
        //center[0]
        val c0Text = mutableListOf("no", "bin", "dec\nhex")
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..2) {
                val width =
                    if (v2 != 0 && v2 != 2) 0 else Tools.convertDp2Px(
                        canterInfoWidth.toFloat(),
                        context
                    ).toInt()
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != 2) viewMargin else viewMargin * 2
                foo += ItemPara(
                    View.generateViewId(),
                    width, 0,
                    lMargin, rMargin, viewMargin, viewMargin,
                    false, 0,
                    true,
                    EnumViewType.TextView, tvFontSize, 0,
                    c0Text[v2]
                )
            }
            c0Para += foo
        }
        var c0Layout = cLayout.getViewById(cPara[0][0].id) as ConstraintLayout
        c0Layout = getConstraintLayout(c0Layout, c0Para)
        //center[1]
        for (v in 0..7) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..9) {
                val width =
                    if (v2 != 0 && v2 != 9) 0 else Tools.convertDp2Px(
                        canterInfoWidth.toFloat(),
                        context
                    ).toInt()
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != 9) viewMargin else viewMargin * 2
                val fs = if (v2 != 0 && v2 != 9) 0f else tvFontSize
                val fc = if (v2 == 0 || v2 == 9) 0 else context.getThemeColor(R.attr.editTextColor)
                foo += ItemPara(
                    View.generateViewId(),
                    width, 0,
                    lMargin, rMargin, viewMargin, viewMargin,
                    false, 0,
                    true,
                    EnumViewType.TextView, fs, fc,
                    if (v2 == 0) v.toString() else "" //no
                )
            }
            c1Para += foo
        }
        var c1Layout = cLayout.getViewById(cPara[1][0].id) as ConstraintLayout
        c1Layout = getConstraintLayout(c1Layout, c1Para)
        //center[2]
        val c2Text = mutableListOf("total", "", "", "", "", "", "", "", "", "")
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..9) {
                val width =
                    if (v2 != 0 && v2 != 9) 0 else Tools.convertDp2Px(
                        canterInfoWidth.toFloat(),
                        context
                    ).toInt()
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != 9) viewMargin else viewMargin * 2
                foo += ItemPara(
                    View.generateViewId(),
                    width, 0,
                    lMargin, rMargin, viewMargin, viewMargin,
                    false, 0,
                    true,
                    EnumViewType.TextView, tvFontSize, 0,
                    c2Text[v2]
                )
            }
            c2Para += foo
        }
        var c2Layout = cLayout.getViewById(cPara[2][0].id) as ConstraintLayout
        c2Layout = getConstraintLayout(c2Layout, c2Para)

        //bottom
        //[0]<< ! >> [1]add
        for (v in 0..1) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    false, 0,
                    false,
                    EnumViewType.ConstraintLayout, 0f, 0,
                    ""
                )
            }
            bPara += foo
        }
        var bLayout = mLayout.getViewById(mPara[2][0].id) as ConstraintLayout
        bLayout = getConstraintLayout(bLayout, bPara)

        //bottom[0] << ! >>
        val b0Text = mutableListOf("<<", "!", ">>")
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..2) {
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != 2) viewMargin else viewMargin * 2
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, viewMargin, viewMargin,
                    false, 0,
                    true,
                    EnumViewType.TextView, 0f, 0,
                    b0Text[v2]
                )
            }
            b0Para += foo
        }
        var b0Layout = bLayout.getViewById(bPara[0][0].id) as ConstraintLayout
        b0Layout = getConstraintLayout(b0Layout, b0Para)

        //bottom[1] add
        val b1Text = mutableListOf("↑", "↓")
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..1) {
                val lMargin = if (v2 != 0) viewMargin else viewMargin * 2
                val rMargin = if (v2 != 1) viewMargin else viewMargin * 2
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    lMargin, rMargin, viewMargin, viewMargin * 2,
                    false, 0,
                    true,
                    EnumViewType.TextView, 0f, 0,
                    b1Text[v2]
                )
            }
            b1Para += foo
        }
        var b1Layout = bLayout.getViewById(bPara[1][0].id) as ConstraintLayout
        b1Layout = getConstraintLayout(b1Layout, b1Para)

        //adView
        for (v in 0..0) {
            val foo: MutableList<ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += ItemPara(
                    View.generateViewId(),
                    0, 0,
                    0, 0, 0, 0,
                    false, 0,
                    false,
                    EnumViewType.AdView, 0f, 0,
                    ""
                )
            }
            adViewPara += foo
        }
        var adLayout = mLayout.getViewById(mPara[3][0].id) as ConstraintLayout
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
                        if (v2.border)
                            child.background = layout.context.getDrawable(R.drawable.border)
                        if (v2.backgroundColor)
                            child.setBackgroundColor(v2.color)
                        layout.addView(child)
                    }
                    EnumViewType.TextView -> {
                        val child = TextView(layout.context)
                        child.gravity = Gravity.CENTER
                        child.typeface = Typeface.MONOSPACE;
                        if (v2.fontSize != 0f)
                            child.textSize = v2.fontSize
                        if (v2.fontColor != 0)
                            child.setTextColor(v2.fontColor)
                        child.id = v2.id
                        if (v2.border)
                            child.background = layout.context.getDrawable(R.drawable.border)
                        if (v2.backgroundColor)
                            child.setBackgroundColor(v2.color)
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