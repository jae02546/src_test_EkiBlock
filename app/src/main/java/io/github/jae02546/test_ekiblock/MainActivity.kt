package io.github.jae02546.test_ekiblock

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {
    private var selLine = 0

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("CutPasteId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val screenSize = Tools.getScreenSize(this.windowManager.currentWindowMetrics)
//        setContentView(MainLayout.makeLayout(this, screenSize))
        val layout = MainLayout.makeLayout(this, screenSize)
        setContentView(layout)
        supportActionBar?.setTitle(R.string.app_label)


        //縦専用モードにするには?

        //レイアウト表示
//        val numDigits = 8
//        Question.makeQuestion(numDigits)
//        showMainLayout(layout)



//        // <<
//        val tapLeftShift = findViewById<TextView>(MainLayout.b0Para[0][0].id)
//        tapLeftShift.setOnClickListener {
//            //Toast.makeText(this, "<<", Toast.LENGTH_SHORT).show()
//            Question.shl(selLine, 8)
//            //レイアウト表示
//            showMainLayout(layout)
//        }
//        // !
//        val tapNot = findViewById<TextView>(MainLayout.b0Para[0][1].id)
//        tapNot.setOnClickListener {
//            //Toast.makeText(this, "!", Toast.LENGTH_SHORT).show()
//            Question.inv(selLine, 8)
//            //レイアウト表示
//            showMainLayout(layout)
//        }
//        // >>
//        val tapRightShift = findViewById<TextView>(MainLayout.b0Para[0][2].id)
//        tapRightShift.setOnClickListener {
//            //Toast.makeText(this, ">>", Toast.LENGTH_SHORT).show()
//            Question.shr(selLine, 8)
//            //レイアウト表示
//            showMainLayout(layout)
//        }
//        // ↑
//        val tapUp = findViewById<TextView>(MainLayout.b1Para[0][0].id)
//        tapUp.setOnClickListener {
//            //Toast.makeText(this, "↑", Toast.LENGTH_SHORT).show()
//            if (selLine > 0)
//                selLine--
//            else
//                selLine = 7
//            //レイアウト表示
//            showMainLayout(layout)
//        }
//        // ↓
//        val tapDown = findViewById<TextView>(MainLayout.b1Para[0][1].id)
//        tapDown.setOnClickListener {
//            //Toast.makeText(this, "↓", Toast.LENGTH_SHORT).show()
//            if (selLine < 7)
//                selLine++
//            else
//                selLine = 0
//            //レイアウト表示
//            showMainLayout(layout)
//        }

        //ここから広告
        MobileAds.initialize(this) { }
        val mAdView = findViewById<AdView>(MainLayout.adViewPara[0][0].id)
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
        //ここまで広告
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        //val bar: TextView = findViewById(R.id.t2)
        //bar.height = 100


        return true
    }

    private fun showMainLayout(view: View) {

        val numDigits = 8
//        val c1Layout = findViewById<ConstraintLayout>(MainLayout.cPara[1][0].id)
//        val c2Layout = findViewById<ConstraintLayout>(MainLayout.cPara[2][0].id)

//        MainLayout.showLayout(c1Layout, MainLayout.c1Para, Question.question, numDigits, selLine)
//        MainLayout.showLayout(view as ConstraintLayout, Question.question, numDigits, selLine)


    }


}


