package io.github.jae02546.test_ekiblock

import androidx.room.Entity
import java.time.LocalDateTime


//問いテーブル
//info3は駅2で未使用なので駅word用の補足にするか?
//公開後に変更されても支障は無い
//問いNo,問い名,かな,ローマ字,補足1,補足2,補足3,url,アイテムリスト<QuestionItemTbl>
@Entity(primaryKeys = ["qNo"])
data class QuestionTbl(
    val qNo: Int = 0,
    val name: String = "",
    val kana: String = "",
    val english: String = "",
    val info1: String = "",
    val info2: String = "",
    val info3: String = "", //駅2未使用
    val url: String = "",
    val qiList: MutableList<QuestionItemTbl> = mutableListOf(),
)

//問いアイテムテーブル
//公開後に変更されても支障は無い
//アイテムNo,名称,かな,ローマ字,補足1,補足2,補足3,url
@Entity(primaryKeys = ["iNo"])
data class QuestionItemTbl(
    val iNo: Int = 0,
    val name: String = "",
    val kana: String = "",
    val english: String = "",
    val info1: String = "",
    val info2: String = "",
    val info3: String = "",
    val url: String = "",
)

//スコアテーブル
//プレイヤーNo,プレイ数,コンプ数
@Entity(primaryKeys = ["pNo"])
data class ScoreTbl(
    val pNo: Int = 0,
    val pCount: Int = 0,
    val cCount: Int = 0,
)

//ラスト状態テーブル
//プレイヤーNo,問いNo,正解Noリスト,正解リスト[行],回答リスト[行][列],持ち札リスト[行][列],開始済み
@Entity(primaryKeys = ["pNo"])
data class LastStateTbl(
    val pNo: Int = 0,
    val qNo: Int = 0,
    val cNoList: MutableList<Int> = mutableListOf(),
    val cList: MutableList<String> = mutableListOf(),
    val aList: MutableList<MutableList<String>> = mutableListOf(),
    val pList: MutableList<MutableList<String>> = mutableListOf(),
    val started: Boolean = false,
)


//上記以外で保存が必要なのはプレイヤーNoぐらい
//プレイヤーNoはprefでok

