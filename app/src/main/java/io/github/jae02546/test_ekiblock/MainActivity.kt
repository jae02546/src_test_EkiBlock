package io.github.jae02546.test_ekiblock

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {
    private var selLine = 0

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("CutPasteId", "ResourceType", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val screenSize = Tools.getScreenSize(this.windowManager.currentWindowMetrics)
//        setContentView(MainLayout.makeLayout(this, screenSize))
        val layout = MainLayout.makeLayout(this, screenSize)
        setContentView(layout)
        supportActionBar?.setTitle(R.string.app_label)

        //縦固定
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        //score イベント
        val sCountY = MainLayout.sPara.count()
        val sCountX = MainLayout.sPara[0].count()
        for (v in 0 until sCountY) {
            for (v2 in 0 until sCountX) {
                val tapS = findViewById<TextView>(MainLayout.sPara[v][v2].id)
                tapS.setOnClickListener {
                    Toast.makeText(this, "s$v$v2", Toast.LENGTH_SHORT).show()


                }
            }
        }

        //question item イベント
        val qiCountY = MainLayout.qiPara.count()
        val qiCountX = MainLayout.qiPara[0].count()
        for (v in 0 until qiCountY) {
            for (v2 in 0 until qiCountX) {
                val tapQi = findViewById<TextView>(MainLayout.qiPara[v][v2].id)
                tapQi.setOnClickListener {
                    Toast.makeText(this, "qi$v$v2", Toast.LENGTH_SHORT).show()


                }
            }
        }

        //answer item イベント
        val aiCountY = MainLayout.aiPara.count()
        val aiCountX = MainLayout.aiPara[0].count()
        for (v in 0 until aiCountY) {
            for (v2 in 0 until aiCountX) {
                val tapAi = findViewById<TextView>(MainLayout.aiPara[v][v2].id)
                tapAi.setOnClickListener {
                    Toast.makeText(this, "ai$v$v2", Toast.LENGTH_SHORT).show()


                }
            }
        }

        //piece item イベント
        val piCountY = MainLayout.piPara.count()
        val piCountX = MainLayout.piPara[0].count()
        for (v in 0 until piCountY) {
            for (v2 in 0 until piCountX) {
                val tapPi = findViewById<TextView>(MainLayout.piPara[v][v2].id)
                tapPi.setOnClickListener {
                    Toast.makeText(this, "pi$v$v2", Toast.LENGTH_SHORT).show()


                }
            }
        }

        //newGame イベント
        val nCountY = MainLayout.nPara.count()
        val nCountX = MainLayout.nPara[0].count()
        for (v in 0 until nCountY) {
            for (v2 in 0 until nCountX) {
                val tapN = findViewById<TextView>(MainLayout.nPara[v][v2].id)
                tapN.setOnClickListener {
                    Toast.makeText(this, "n$v$v2", Toast.LENGTH_SHORT).show()


                }
            }
        }


//        // !
//        val tapNot = findViewById<TextView>(MainLayout.b0Para[0][1].id)
//        tapNot.setOnClickListener {
//            //Toast.makeText(this, "!", Toast.LENGTH_SHORT).show()
//            Question.inv(selLine, 8)
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


