package io.github.jae02546.test_ekiblock

import androidx.room.Entity
import java.time.Duration
import java.time.LocalDateTime

//フォルダテーブル
//fNoは8桁ユニーク、fNoでsortした順に表示する
//フォルダまたはファイルはmax4階層でレベル0-3
//レベル3は必ずファイル
//各フォルダ配下は最大99のフォルダまたはファイルが設定可能
//8-7桁レベル0 6-5桁レベル1 4-3桁レベル2 2-1桁レベル3
//
//レベル0 8-7桁01-99 6-1桁000000
//01000000
//   ~
//99000000
//
//レベル1 6-5桁01-99 4-1桁0000
//xx010000
//   ~
//xx990000
//
//レベル2 4-3桁01-99 2-1桁00
//xxxx0100
//   ~
//xxxx9900
//
//レベル3 2-1桁01-99
//xxxxxx01
//   ~
//xxxxxx99
//
//fNoは公開後変更可能、qNoは変更不可（端末側プレイ記録との整合性のため）
//qNoが0の場合はフォルダ、1以上の場合はファイル（ファイルとは問いのこと）
//フォルダNo（表示順）,問いNo
@Entity(primaryKeys = ["fNo"])
data class FolderTbl(
    val fNo: Int = 0,
    val qNo: Int = 0,
)

//フォルダ情報テーブル
//フォルダNoはユニーク
//フォルダクラスのfNoに対応するが問いの場合は問いクラスを使用するのでデータは必要ない
//フォルダNo,フォルダ名,かな,ローマ字,補足1,補足2,補足3
@Entity(primaryKeys = ["fNo"])
data class FolderInfoTbl(
    val fNo: Int = 0,
    val name: String = "",
    val kana: String = "",
    val english: String = "",
    val info1: String = "",
    val info2: String = "", //未使用
    val info3: String = "", //未使用
)

//問いテーブル
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

//プレイヤー回答テーブル
//comp時間 複数回compした場合は最後のcomp
//プレイ中 シャッフル後一度でも入替があるとtrue
//offset 次回起動時にrecyclerviewを前回表示状態に戻すためのoffset
//プレイヤーNo,問いNo,回答リスト<アイテムNo>,回答compリスト<compアイテムNo>,comp時間,プレイ中,offset
@Entity(primaryKeys = ["pNo", "qNo"])
data class AnswerTbl(
    val pNo: Int = 0,
    val qNo: Int = 0,
    val aList: MutableList<Int> = mutableListOf(),
    val aCompList: MutableList<Int> = mutableListOf(),
    val cTime: Duration = Duration.ZERO,
    val inPlay: Boolean = false,
    val rvOffset: Int = 0,
)

//最速comp時間テーブル
//問いNo,プレイヤーNo,最速comp時間
@Entity(primaryKeys = ["qNo"])
data class FastestTbl(
    val qNo: Int = 0,
    val pNo: Int = 0,
    val cTime: Duration = Duration.ZERO,
)

//フォルダ展開状態テーブル
//フォルダ00000000は「新着」FolderTblには存在しない
//フォルダNo,展開フラグ（trueで展開表示）
@Entity(primaryKeys = ["fNo"])
data class FolderExpandTbl(
    val fNo: Int = 0,
    val expand: Boolean = false,
)

//終了時状態テーブル
//次回起動時に画面復元に必要な項目を保存するテーブル
//プレイヤー毎に持つ必要はあるか
//プレイヤーを切り替えた場合にフォルダはそのままの方が自然ではないか?
//プレイヤー切替はフォルダ画面でしかできない
//前回終了時が問い画面でも画面復元はフォルダ画面まででよいのでは?
//問い画面まで戻すとプレイヤーが見えないので間違える可能性が?
//とした場合保存が必要なのは
//・プレイヤーNo
//・フォルダ展開状態
//・フォルダRecyclerViewのoffset
//フォルダ展開状態は別テーブルで保存される、その他はprefでok?
//プレイヤーNo,フォルダ展開状態,フォルダRecyclerViewのoffset
//@Entity(primaryKeys = ["pNo"])
//data class PlayStateTbl(
//    val pNo: Int = 0,
//
//
//)

