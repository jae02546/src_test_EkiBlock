package io.github.jae02546.test_ekiblock

import androidx.room.Entity
import java.time.LocalDateTime


//問いテーブル
//駅並べ2のまま、使用する項目は名称、かな、英語ぐらい?
//info3を駅word用の補足にするか?
//公開後に変更されても支障は無い
//以下、駅並べ2のコメント
//------------------------------------------------------------------
//qNoはユニーク
//公開後のqNo,qiNoListの変更不可（端末側プレイ記録との整合性のため）
//qiNoListはqiList内のiNoと同じものだがNoだけのリストが必要になる場合が多いため別に持つ
//鉄道データでは補足1がgssでの補足1（廃線、休止）+補足2（通称等）、補足2が補足3（より詳細な補足）
//問いNo,問い名,かな,ローマ字,補足1,補足2,補足3,url,アイテムリスト<QuestionItemTbl>,アイテムNoリスト<Int>,,アイテムCompNoリスト<Int>
@Entity(primaryKeys = ["qNo"])
data class QuestionTbl(
    val qNo: Int = 0,
    val createdDate: LocalDateTime = LocalDateTime.MIN,
    val updateDate: LocalDateTime = LocalDateTime.MIN,
    val name: String = "",
    val kana: String = "",
    val english: String = "",
    val info1: String = "",
    val info2: String = "",
    val info3: String = "", //未使用
    val url: String = "",
    val qiList: MutableList<QuestionItemTbl> = mutableListOf(),
)

//問いアイテムテーブル
//駅並べ2のまま、使用する項目は名称、かな、英語ぐらい?
//公開後に変更されても支障は無い
//以下、駅並べ2のコメント
//------------------------------------------------------------------
//iNoはユニークなこと
//公開後のiNoの変更不可（端末側プレイ記録との整合性のため）
//元データとなるgss上の順序はゲームでの正解としたいアイテム順（iNo順ではない）
//iNoはQuestionTblのqiNoListと同じものだが区別するためにqiNoではなくiNoとする
//基本はqiNoListを使用しiNoは使わない（ダウンロード時の変換では使用）
//鉄道データでは補足1は補足、補足2は（廃駅、休止」）、補足3は都道府県名
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

//回答テーブル
//起動時及びプレイヤー変更時に以前の状態に戻すため回答状態を記録する
//プレイヤーNo,問いNo,正解リスト,回答リスト,ピースリスト
@Entity(primaryKeys = ["pNo"])
data class AnswerTbl(
    val pNo: Int = 0,
    val qNo: Int = 0,
    val cList: MutableList<String> = mutableListOf(),
    val aList: MutableList<String> = mutableListOf(),
    val pList: MutableList<String> = mutableListOf(),
)


//スコアテーブル
//プレイヤーNo,プレイ回数,勝ち,負け,途中で止めた
@Entity(primaryKeys = ["pNo"])
data class ScoreTbl(
    val pNo: Int = 0,
    val playCount: Int = 0,
    val win: Int = 0,
    val lose: Int = 0,
    val stop: Int = 0,
)

//終了時状態テーブル
//起動時に画面復元に必要な項目を保存するテーブル
//回答は別にテーブルが有るので必要なし
//後はプレイヤーNoぐらいなのでprefでok?
//@Entity(primaryKeys = ["pNo"])
//data class PlayStateTbl(
//    val pNo: Int = 0,
//
//
//)

