package io.github.jae02546.test_ekiblock

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

        //起動時ダウンロード表示
        val top = findViewById<TextView>(MainLayout.sPara[0][0].id)
        top.text = getString(R.string.top_name_us)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //メニューが選択された場合に発生
        return when (item.itemId) {
            R.id.menuPlayer1, R.id.menuPlayer2, R.id.menuPlayer3, R.id.menuPlayer4, R.id.menuPlayer5 -> {
                //プレイヤーNo保存
                var playerNo = 0
                if (item.itemId == R.id.menuPlayer1)
                    playerNo = 1
                if (item.itemId == R.id.menuPlayer2)
                    playerNo = 2
                if (item.itemId == R.id.menuPlayer3)
                    playerNo = 3
                if (item.itemId == R.id.menuPlayer4)
                    playerNo = 4
                if (item.itemId == R.id.menuPlayer5)
                    playerNo = 5
                //プレーヤ選択前のプレーヤNoをpreferenceより取得
                val beforePlayerNo = Tools.getPrefInt(
                    this,
                    getString(R.string.pref_playerNo_key),
                    getString(R.string.pref_playerNo_defaultValue).toInt()
                )
                //選択されたプレーヤが現状と違う場合は
                //プレーヤNoの書込みとメニュー、メイン画面の更新
                if (beforePlayerNo != playerNo) {
                    //プレーヤNo preferences書込
                    Tools.putPrefInt(this, getString(R.string.pref_playerNo_key), playerNo)
                    //プレーヤが変更されたのでメニュー更新onPrepareOptionsMenu
                    invalidateOptionsMenu()


                    //プレーヤが変更されたのでrvData再表示
                    //rvDataSetChanged(false)


                }
                true
            }
            R.id.menuRecord -> {
                //プレイ記録
                //プレーヤ回答情報表示
                val pnMap: MutableMap<Int, String> = mutableMapOf()
                for (i in 1..5)
                    pnMap += i to getPlayerName(i)
                val inflater = LayoutInflater.from(this)
                val layout = inflater.inflate(
                    R.layout.dialog_player,
                    findViewById<ConstraintLayout>(R.id.playLayout)
                )
                val record = Tools.getPlayStatus(this, pnMap)
                layout.findViewById<TextView>(R.id.playRecord1).text = record[0]
                layout.findViewById<TextView>(R.id.playRecord2).text = record[1]
                layout.findViewById<TextView>(R.id.player1a).text = record[2]
                layout.findViewById<TextView>(R.id.player1b).text = record[3]
                layout.findViewById<TextView>(R.id.player1c).text = record[4]
                layout.findViewById<TextView>(R.id.player2a).text = record[5]
                layout.findViewById<TextView>(R.id.player2b).text = record[6]
                layout.findViewById<TextView>(R.id.player2c).text = record[7]
                layout.findViewById<TextView>(R.id.player3a).text = record[8]
                layout.findViewById<TextView>(R.id.player3b).text = record[9]
                layout.findViewById<TextView>(R.id.player3c).text = record[10]
                layout.findViewById<TextView>(R.id.player4a).text = record[11]
                layout.findViewById<TextView>(R.id.player4b).text = record[12]
                layout.findViewById<TextView>(R.id.player4c).text = record[13]
                layout.findViewById<TextView>(R.id.player5a).text = record[14]
                layout.findViewById<TextView>(R.id.player5b).text = record[15]
                layout.findViewById<TextView>(R.id.player5c).text = record[16]
                AlertDialog.Builder(this)
                    .setTitle("プレイ記録")
                    .setView(layout)
                    .setPositiveButton("OK", null)
                    .show()
                true
            }
            R.id.menuSetting -> {
                //設定画面表示
                startGettingSettingResult.launch(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //ここではメニュー項目の変更をまとめて行う
        //androidDevによると実行時のメニュー変更はこのイベントですることになっている
        //このイベントはメニュー項目がアプリバーに表示されるときに発生する
        //具体的には、起動時と折り畳まれているメニューを開いた場合に発生している
        //他の箇所からinvalidateOptionsMenuで発生させることも出来る
        //invalidateOptionsMenuはプレーヤ選択後と設定画面から戻る時に実行している
        //プレーヤ選択では同じプレーヤを選択した場合は実行していない
        //設定ではプレーヤ名を変更していなくても発生
        //メニューを開き何もせず閉じた場合も発生しない（メニュー以外をタッチで閉じる）

        //preferenceよりplayerNo取得
        val playerNo = Tools.getPrefInt(
            this,
            getString(R.string.pref_playerNo_key),
            getString(R.string.pref_playerNo_defaultValue).toInt()
        )
        //メニューのプレイヤー名設定
        val mi1: MenuItem = menu?.findItem(R.id.menuPlayer1) as MenuItem
        mi1.title = getPlayerName(1)
        val mi2: MenuItem = menu?.findItem(R.id.menuPlayer2) as MenuItem
        mi2.title = getPlayerName(2)
        val mi3: MenuItem = menu?.findItem(R.id.menuPlayer3) as MenuItem
        mi3.title = getPlayerName(3)
        val mi4: MenuItem = menu?.findItem(R.id.menuPlayer4) as MenuItem
        mi4.title = getPlayerName(4)
        val mi5: MenuItem = menu?.findItem(R.id.menuPlayer5) as MenuItem
        mi5.title = getPlayerName(5)
        //選択されているプレイヤーのチェックとアイコン設定
        val mi: MenuItem = menu?.findItem(R.id.menuPlayer) as MenuItem
        when (playerNo) {
            1 -> {
                mi.setIcon(R.drawable.ic_person_fill0_wght400_grad0_opsz48_1)
                mi1.isChecked = true
            }
            2 -> {
                mi.setIcon(R.drawable.ic_person_fill0_wght400_grad0_opsz48_2)
                mi2.isChecked = true
            }
            3 -> {
                mi.setIcon(R.drawable.ic_person_fill0_wght400_grad0_opsz48_3)
                mi3.isChecked = true
            }
            4 -> {
                mi.setIcon(R.drawable.ic_person_fill0_wght400_grad0_opsz48_4)
                mi4.isChecked = true
            }
            5 -> {
                mi.setIcon(R.drawable.ic_person_fill0_wght400_grad0_opsz48_5)
                mi5.isChecked = true
            }
        }

        return true
    }

    private fun getPlayerName(playerNo: Int): String {
        //プレーヤ名取得
        when (playerNo) {
            1 -> {
                return Tools.getPrefStr(
                    this,
                    getString(R.string.setting_playerName1_key),
                    getString(R.string.setting_playerName1_defaultValue)
                )
            }
            2 -> {
                return Tools.getPrefStr(
                    this,
                    getString(R.string.setting_playerName2_key),
                    getString(R.string.setting_playerName2_defaultValue)
                )
            }
            3 -> {
                return Tools.getPrefStr(
                    this,
                    getString(R.string.setting_playerName3_key),
                    getString(R.string.setting_playerName3_defaultValue)
                )
            }
            4 -> {
                return Tools.getPrefStr(
                    this,
                    getString(R.string.setting_playerName4_key),
                    getString(R.string.setting_playerName4_defaultValue)
                )
            }
            5 -> {
                return Tools.getPrefStr(
                    this,
                    getString(R.string.setting_playerName5_key),
                    getString(R.string.setting_playerName5_defaultValue)
                )
            }
            else -> {
                return ""
            }
        }
    }

    //設定画面の表示開始と結果の取得
    private val startGettingSettingResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            //プレーヤ名変更の可能性があるのでメニュー更新onPrepareOptionsMenu
            invalidateOptionsMenu()
            //設定画面から<-戻るで発生
            if (result?.resultCode == Activity.RESULT_OK) {
                result.data?.let { data: Intent ->
                    //特に無し
                }
            }
        }




    private fun showMainLayout(view: View) {

        val numDigits = 8
//        val c1Layout = findViewById<ConstraintLayout>(MainLayout.cPara[1][0].id)
//        val c2Layout = findViewById<ConstraintLayout>(MainLayout.cPara[2][0].id)

//        MainLayout.showLayout(c1Layout, MainLayout.c1Para, Question.question, numDigits, selLine)
//        MainLayout.showLayout(view as ConstraintLayout, Question.question, numDigits, selLine)


    }


}


