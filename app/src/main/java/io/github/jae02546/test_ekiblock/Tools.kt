package io.github.jae02546.test_ekiblock

import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowMetrics
import androidx.annotation.RequiresApi

object Tools {


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

}