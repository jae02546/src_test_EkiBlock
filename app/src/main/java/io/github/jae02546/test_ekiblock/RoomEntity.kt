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

//終了時状態テーブル
//起動時及びプレイヤー変更時に以前の状態に戻すためのデータ
//プレイヤーNo,問いNo,正解リスト,回答リスト,ピースリスト
@Entity(primaryKeys = ["pNo"])
data class LastStateTbl(
    val pNo: Int = 0,
    val qNo: Int = 0,
    val cList: MutableList<String> = mutableListOf(),
    val aList: MutableList<String> = mutableListOf(),
    val pList: MutableList<String> = mutableListOf(),
)


//上記以外で保存が必要なのはプレイヤーNoぐらいなのでprefでok

