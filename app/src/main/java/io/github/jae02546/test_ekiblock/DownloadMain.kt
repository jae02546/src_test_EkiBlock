package io.github.jae02546.test_ekiblock

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.http.GET
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object DownloadMain {
    //githubのfolder構造
    //https://jae02546.github.io/ekiword/
    // >data001 更新データ1
    //  question.json
    // >data002 更新データ2
    //  question.json
    // >test001 更新データ1テスト
    //  question.json
    // >manual 取説フォルダ
    // index.html 取説トップ
    // updateSchedule.json 更新スケジュール
    // updateScheduleTest.json 更新スケジュールテスト
    //
    //起動時
    //通常モードではupdateSchedules.jsonを読込
    //保守モードではupdateScheduleTest.jsonを読込
    //更新時、数MB程度なら全データダウンロードしても大丈夫?
    //数秒なので更新時のみなら大丈夫そう
    //更新有無チェックだけなら1-2秒か?
    //
    //

    private const val usJson = "updateSchedule.json" //更新スケジュールjsonファイル
    private const val ustJson = "updateScheduleTest.json" //テスト用更新スケジュールjsonファイル
    private const val qJson = "question.json" //問いjsonファイル

    //更新スケジュールデータクラス
    //開始日時,フォルダ
    data class UpdateScheduleDc(
        var startDate: LocalDateTime = LocalDateTime.MAX,
        var folder: String = "",
    )

    //startDateはLocalDateTimeとしたいがうまくいかないので
    //取り敢えずStringで取得したあとparseで変換する
    data class UpdateScheduleDc2(
        var startDate: String = "",
        var folder: String = "",
    )

    interface UpdateScheduleService {
        @GET(usJson)
        //fun getList(): Call<List<UpdateScheduleDc>>
        fun getList(): Call<List<UpdateScheduleDc2>>
    }

    interface UpdateScheduleTestService {
        @GET(ustJson)
        //fun getList(): Call<List<UpdateScheduleDc>>
        fun getList(): Call<List<UpdateScheduleDc2>>
    }

    //更新対象のデータがあるか確認する
    fun checkOfUpdate(lastUpdate: LocalDateTime, usList2: List<UpdateScheduleDc2>): String {
        //端末に保存されている最終更新日時より後の開始日時のスケジュールがあれば更新
        //対象となるスケジュールが複数ある場合は本日以前で最も新しいスケジュールとする

        //startDateの降順にsort
        val us: MutableList<UpdateScheduleDc> = mutableListOf()
        for (v in usList2)
            us += UpdateScheduleDc(LocalDateTime.parse(v.startDate), v.folder)
        us.sortBy { it.startDate }
        us.reverse()
        //本日以前で、最終更新日より後のスケジュールがあれば更新
        val now = LocalDateTime.now()
        for (v in us)
            if (v.startDate <= now && v.startDate > lastUpdate) {
                return v.folder
            }
        //更新無しの場合は空文字列
        return ""
    }

    interface QuestionBase64Service {
        @GET(qJson)
        fun getBase64List(): Call<List<String>>
    }

    //QuestionTblとの違い
    //  createdDate,updateDateは無い（アプリ側で未使用）
    data class QuestionTbl2(
        val qNo: Int = 0,
        val createdDate: String = "",
        val updateDate: String = "",
        val name: String = "",
        val kana: String = "",
        val english: String = "",
        val info1: String = "",
        val info2: String = "",
        val info3: String = "",
        val url: String = "",
        val qiList: MutableList<QuestionItemTbl> = mutableListOf(),
    )

    //base64で取得した問いリストをList<QuestionTbl>に変換する
    fun createQuestionList(base64List: List<String>, aesPass: String): List<QuestionTbl> {
        //AESのpass 12文字に足りない場合は後ろに0を付ける
        //12文字のpassをbase64して16文字にする
        //iv大文字、key小文字
        val pass = (aesPass + "0".repeat(12)).substring(0, 12)

        val iv: String = Base64.getEncoder().encodeToString(pass.uppercase().toByteArray())
        val key: String = Base64.getEncoder().encodeToString(pass.lowercase().toByteArray())
        val listType: Type = object : TypeToken<QuestionTbl2>() {}.type

        val qList2: MutableList<QuestionTbl2> = mutableListOf()
        for (v in base64List) {
            val foo: QuestionTbl2 = Gson().fromJson(v.decrypt(iv, key), listType)
            qList2 += foo
        }
        val qList: MutableList<QuestionTbl> = mutableListOf()
        for (v in qList2) {
            val foo = QuestionTbl(
                v.qNo,
                v.name,
                v.kana,
                v.english,
                v.info1,
                v.info2,
                v.info3,
                v.url,
                v.qiList
            )
            qList += foo
        }

        return qList
    }

    private fun String.encrypt(initializationKey: String, secretKey: String): String {
        //それぞれ16文字にする
        //val initializationKey = "ABCDEFGHIJKLMNOP"
        //val secretKey = "abcdefghijklmnop"
        val iv = IvParameterSpec(initializationKey.toByteArray())
        val key = SecretKeySpec(secretKey.toByteArray(), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)

        val byteResult = cipher.doFinal(this.toByteArray())

        return android.util.Base64.encodeToString(byteResult, android.util.Base64.NO_WRAP)
    }

    private fun String.decrypt(initializationKey: String, secretKey: String): String {
        //それぞれ16文字にする
        //val initializationKey = "abcdefghijklmnop"
        //val secretKey = "ABCDEFGHIJKLMNOP"
        val iv = IvParameterSpec(initializationKey.toByteArray())
        val key = SecretKeySpec(secretKey.toByteArray(), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, iv)

        val byteResult =
            cipher.doFinal(android.util.Base64.decode(this, android.util.Base64.NO_WRAP))

        return String(byteResult)
    }


}
