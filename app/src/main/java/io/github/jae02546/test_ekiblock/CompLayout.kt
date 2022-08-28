package io.github.jae02546.test_ekiblock

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.jae02546.test_ekiblock.MainLayout.getThemeColor

object CompLayout {

    //解答駅数
    private const val mAnswers = 5

    //駅アイテム数
    private const val mAnswerItems = 4

    //main main内item main内item内item1 main内item内item2 main内item内item3
    private var mPara: MutableList<MutableList<MainLayout.ItemPara>> = mutableListOf()
    private var iPara: MutableList<MutableList<MutableList<MainLayout.ItemPara>>> = mutableListOf()
    private var i1Para: MutableList<MutableList<MutableList<MainLayout.ItemPara>>> = mutableListOf()
    private var i2Para: MutableList<MutableList<MutableList<MainLayout.ItemPara>>> = mutableListOf()
    private var i3Para: MutableList<MutableList<MutableList<MainLayout.ItemPara>>> = mutableListOf()

    //レイアウト作成
    fun makeLayout(context: Context, screenSize: MutableList<Int>, pNo: Int): ConstraintLayout {
        //パラメータ初期化
        mPara = mutableListOf()
        iPara = mutableListOf()
        i1Para = mutableListOf()
        i2Para = mutableListOf()
        i3Para = mutableListOf()
        //コンプリスト取得
        val compList = Tools.getCompList(context, pNo)
        for (v in 0 until mAnswers) {
            if (compList.count() < mAnswers)
                compList += QuestionItemTbl()
            else
                break
        }
        //main
        val gd = GradientDrawable()
        gd.setStroke(
            Tools.convertDp2Px(1f, context).toInt(),
            context.getThemeColor(R.attr.colorButtonNormal)
        )
        val ldTopBottom = LayerDrawable(arrayOf<Drawable>(gd))
        ldTopBottom.setLayerInset(0, -10, 0, -10, 0)
        val ldBottom = LayerDrawable(arrayOf<Drawable>(gd))
        ldBottom.setLayerInset(0, -10, -10, -10, 0)
        val iHeight = screenSize[0] / 5
        for (v in 0 until mAnswers) {
            val foo: MutableList<MainLayout.ItemPara> = mutableListOf()
            for (v2 in 0..0) {
                foo += MainLayout.ItemPara(
                    View.generateViewId(),
                    0, iHeight,
                    0, 0, 0, 0,
                    if (v == 0) ldTopBottom else ldBottom,
                    MainLayout.EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                    ""
                )
            }
            mPara += foo
        }
        var mLayout = ConstraintLayout(context)
        mLayout = MainLayout.getConstraintLayout(mLayout, mPara)
        mLayout.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        //main内item[廃駅/都道府県][駅名][ - ]
        val iWidth = screenSize[0] / 8
        var iLayout: MutableList<ConstraintLayout> = mutableListOf()
        for (v in 0 until mAnswers) {
            val vPara: MutableList<MutableList<MainLayout.ItemPara>> = mutableListOf()
            for (v2 in 0..0) {
                val v2Para: MutableList<MainLayout.ItemPara> = mutableListOf()
                for (v3 in 0..2) {
                    v2Para += MainLayout.ItemPara(
                        View.generateViewId(),
                        if (v3 != 1) iWidth else 0, 0,
                        0, 0, 0, 0,
                        LayerDrawable(arrayOf<Drawable>()),
                        MainLayout.EnumViewType.ConstraintLayout, 0f, 0f, 0, Gravity.CENTER,
                        //v.toString() + v2.toString() + v3.toString()
                        ""
                    )
                }
                vPara += v2Para
            }
            iPara += vPara
            iLayout += mLayout.getViewById(mPara[v][0].id) as ConstraintLayout
            iLayout[v] = MainLayout.getConstraintLayout(iLayout[v], vPara)
        }
        //main内item内item1[ - ][廃駅][都道府県]
        for (v in 0 until mAnswers) {
            val vPara: MutableList<MutableList<MainLayout.ItemPara>> = mutableListOf()
            for (v2 in 0..2) {
                val v2Para: MutableList<MainLayout.ItemPara> = mutableListOf()
                for (v3 in 0..0) {
                    v2Para += MainLayout.ItemPara(
                        View.generateViewId(),
                        0, 0,
                        0, 0, 0, 0,
                        LayerDrawable(arrayOf<Drawable>()),
                        MainLayout.EnumViewType.TextView, 0f, 10f, 0, Gravity.CENTER,
                        //v.toString() + v2.toString() + v3.toString()
                        if (v2 == 0) "" else {
                            if (v2 == 1) compList[v].info2 else compList[v].info3
                        }
                    )
                }
                vPara += v2Para
            }
            i1Para += vPara
            var i1Layout = iLayout[v].getViewById(iPara[v][0][0].id) as ConstraintLayout
            i1Layout = MainLayout.getConstraintLayout(i1Layout, vPara)
        }
        //main内item内item2[かな][駅名][ローマ字][補足3]
        val vWeight: MutableList<Float> = mutableListOf(1.0f, 1.0f, 1.0f, 1.0f)
        val fSize: MutableList<Float> = mutableListOf(10f, 0f, 10f, 10f)
        val fColor: MutableList<Int> =
            mutableListOf(0, context.getThemeColor(R.attr.editTextColor), 0, 0)
        val gravity: MutableList<Int> =
            mutableListOf(
                Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM,
                Gravity.CENTER,
                Gravity.CENTER_HORIZONTAL + Gravity.TOP,
                Gravity.CENTER
            )
        for (v in 0 until mAnswers) {
            val vPara: MutableList<MutableList<MainLayout.ItemPara>> = mutableListOf()
            //for (v2 in 0..3) {
            for (v2 in 0..2) {
                val v2Para: MutableList<MainLayout.ItemPara> = mutableListOf()
                for (v3 in 0..0) {
                    v2Para += MainLayout.ItemPara(
                        View.generateViewId(),
                        0, 0,
                        0, 0, 0, 0,
                        LayerDrawable(arrayOf<Drawable>()),
                        MainLayout.EnumViewType.TextView,
                        vWeight[v2],
                        fSize[v2],
                        fColor[v2],
                        gravity[v2],
                        //v.toString() + v2.toString() + v3.toString()
                        if (v2 == 0) compList[v].kana else {
                            if (v2 == 1) compList[v].name else {
                                if (v2 == 2) compList[v].english else compList[v].info1
                            }
                        }
                    )
                }
                vPara += v2Para
            }
            i2Para += vPara
            var i2Layout = iLayout[v].getViewById(iPara[v][0][1].id) as ConstraintLayout
            i2Layout = MainLayout.getConstraintLayout(i2Layout, vPara)
        }

        return mLayout
    }


}