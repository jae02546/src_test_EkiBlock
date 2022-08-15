package io.github.jae02546.test_ekiblock
package io.github.jae02546.narabe

import android.content.Context
import java.time.LocalDateTime

object RoomMain {

    //フォルダテーブルとフォルダ情報テーブル作成
    //初期起動時とテーブル更新時に使用
    fun createFolderTbl(context: Context, fList: List<FolderTbl>, fiList: List<FolderInfoTbl>) {
        val db = NarabeDatabase.getInstance(context)
        val fDao = db.folderDao()
        val fiDao = db.folderInfoDao()
        val feDao = db.folderExpandDao()
        //全データ削除
        val fRet = fDao.deleteAll()
        val fiRet = fiDao.deleteAll()
        val feRet = feDao.deleteAll() //feは削除のみ
        //Log.d("delete f fi fe", "$fRet $fiRet $feRet")
        //データ追加
        for (v in fList)
            fDao.insert(v)
        for (v in fiList)
            fiDao.insert(v)
    }

    //問いテーブル作成
    //初期起動時とテーブル更新時に使用
    fun createQuestionTbl(context: Context, qList: List<QuestionTbl>) {
        val db = NarabeDatabase.getInstance(context)
        val qDao = db.questionDao()
        //全データ削除
        val qRet = qDao.deleteAll()
        //Log.d("delete q", "$qRet")
        //データ追加
        for (v in qList)
            qDao.insert(v)
    }

    //フォルダlist取得
    fun getFolderList(context: Context): MutableList<FolderTbl> {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.folderDao()
        return dao.getRecordAll() ?: mutableListOf()
    }

    //フォルダ情報map取得
    fun getFolderInfoMap(context: Context): MutableMap<Int, FolderInfoTbl> {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.folderInfoDao()
        val rec = dao.getRecordAll() ?: mutableListOf()
        val map: MutableMap<Int, FolderInfoTbl> = mutableMapOf()
        for (v in rec)
            map += v.fNo to v
        return map
    }

    //問いmap取得
    fun getQuestionMap(context: Context): MutableMap<Int, Tools.QuestionDc> {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.questionDao()
        val rec = dao.getRecordAll() ?: mutableListOf()
        val map: MutableMap<Int, Tools.QuestionDc> = mutableMapOf()
        for (v in rec) {
            val qiNoList: MutableList<Int> = mutableListOf()
            val qiCompNoList: MutableList<Int> = mutableListOf()
            for (v2 in v.qiList) {
                qiNoList += v2.iNo
                qiCompNoList += v2.iNo / 10 //下1桁はcomp判断に使わない
            }
            val foo = Tools.QuestionDc(
                v.createdDate,
                v.updateDate,
                v.name,
                v.kana,
                v.english,
                v.info1,
                v.info2,
                v.info3,
                v.url,
                v.qiList,
                qiNoList,
                qiCompNoList
            )
            map += v.qNo to foo
        }
        return map
    }

//    //問いレコード取得
//    fun getQuestionRecord(context: Context, qNo: Int): QuestionTbl? {
//        val db = NarabeDatabase.getInstance(context)
//        val dao = db.questionDao()
//        val rec = dao.getRecord(qNo) ?: mutableListOf()
//        return if (rec.size > 0)
//            rec[0]
//        else
//            null
//    }

    //プレイヤー回答List取得
    fun getAnswerList(context: Context): MutableList<AnswerTbl> {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.answerDao()
        return dao.getRecordAll() ?: mutableListOf()
    }

    //プレイヤー回答レコード取得
    fun getAnswerRecord(context: Context, pNo: Int, qNo: Int): AnswerTbl? {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.answerDao()
        val rec = dao.getRecord(pNo, qNo) ?: mutableListOf()
        return if (rec.size > 0)
            rec[0]
        else
            null
    }

    //プレイヤー回答レコード書込
    fun putAnswerRecord(context: Context, answerTbl: AnswerTbl): Long {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.answerDao()
        val rec = dao.getRecord(answerTbl.pNo, answerTbl.qNo)
        return if (rec == null || rec.size <= 0) { //リストなのでサイズも必要
            //追加
            dao.insert(answerTbl)
        } else {
            //更新
            dao.update(answerTbl).toLong()
        }
    }

    //最速comp時間list取得
    fun getFastestList(context: Context): MutableList<FastestTbl> {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.fastestDao()
        return dao.getRecordAll() ?: mutableListOf()
    }

    //最速comp時間レコード取得
    fun getFastestRecord(context: Context, qNo: Int): FastestTbl? {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.fastestDao()
        val rec = dao.getRecord(qNo) ?: mutableListOf()
        return if (rec.size > 0)
            rec[0]
        else
            null
    }

    //最速comp時間レコード書込
    fun putFastestRecord(context: Context, fastestTbl: FastestTbl): Long {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.fastestDao()
        val rec = dao.getRecord(fastestTbl.qNo)
        return if (rec == null || rec.size <= 0) { //リストなのでサイズも必要
            //追加
            dao.insert(fastestTbl)
        } else {
            //更新
            dao.update(fastestTbl).toLong()
        }
    }

    //フォルダ展開状態map取得
    fun getFolderExpandMap(context: Context): MutableMap<Int, FolderExpandTbl> {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.folderExpandDao()
        val rec = dao.getRecordAll() ?: mutableListOf()
        val map: MutableMap<Int, FolderExpandTbl> = mutableMapOf()
        for (v in rec)
            map += v.fNo to v
        return map
    }

    //フォルダ展開状態レコード書込
    fun putFolderExpandRecord(context: Context, folderExpandTbl: FolderExpandTbl): Long {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.folderExpandDao()
        val rec = dao.getRecord(folderExpandTbl.fNo)
        return if (rec == null || rec.size <= 0) { //リストなのでサイズも必要
            //追加
            dao.insert(folderExpandTbl)
        } else {
            //更新
            dao.update(folderExpandTbl).toLong()
        }
    }

    //フォルダ展開状態全レコード削除
    fun deleteAllFolderExpandRecord(context: Context): Int {
        val db = NarabeDatabase.getInstance(context)
        val dao = db.folderExpandDao()
        return dao.deleteAll()
    }


}

