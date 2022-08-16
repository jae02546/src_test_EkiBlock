package io.github.jae02546.test_ekiblock

import android.content.Context

object RoomMain {

    //問いテーブル作成
    //初期起動時とテーブル更新時に使用
    fun createQuestionTbl(context: Context, qList: List<QuestionTbl>) {
        val db = EkiWordDatabase.getInstance(context)
        val qDao = db.questionDao()
        //全データ削除
        val qRet = qDao.deleteAll()
        //Log.d("delete q", "$qRet")
        //データ追加
        for (v in qList)
            qDao.insert(v)
    }

    //問いmap取得
    fun getQuestionMap(context: Context): MutableMap<Int, Tools.QuestionDc> {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.questionDao()
        val rec = dao.getRecordAll() ?: mutableListOf()
        val map: MutableMap<Int, Tools.QuestionDc> = mutableMapOf()
        for (v in rec) {
            val foo = Tools.QuestionDc(
                v.name,
                v.kana,
                v.english,
                v.info1,
                v.info2,
                v.info3,
                v.url,
                v.qiList,
            )
            map += v.qNo to foo
        }
        return map
    }

    //回答List取得
    fun getAnswerList(context: Context): MutableList<AnswerTbl> {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.answerDao()
        return dao.getRecordAll() ?: mutableListOf()
    }

    //回答レコード取得
    fun getAnswerRecord(context: Context, pNo: Int, qNo: Int): AnswerTbl? {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.answerDao()
        val rec = dao.getRecord(pNo, qNo) ?: mutableListOf()
        return if (rec.size > 0)
            rec[0]
        else
            null
    }

    //回答レコード書込
    fun putAnswerRecord(context: Context, answerTbl: AnswerTbl): Long {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.answerDao()
        val rec = dao.getRecord(answerTbl.pNo, answerTbl.qNo)
        return if (rec == null || rec.size <= 0) {
            //追加
            dao.insert(answerTbl)
        } else {
            //更新
            dao.update(answerTbl).toLong()
        }
    }

    //スコアList取得
    fun getScoreList(context: Context): MutableList<ScoreTbl> {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.scoreDao()
        return dao.getRecordAll() ?: mutableListOf()
    }

    //スコアレコード取得
    fun getScoreRecord(context: Context, pNo: Int): ScoreTbl? {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.scoreDao()
        val rec = dao.getRecord(pNo) ?: mutableListOf()
        return if (rec.size > 0)
            rec[0]
        else
            null
    }

    //スコアレコード書込
    fun putScoreRecord(context: Context, scoreTbl: ScoreTbl): Long {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.scoreDao()
        val rec = dao.getRecord(scoreTbl.pNo)
        return if (rec == null || rec.size <= 0) {
            //追加
            dao.insert(scoreTbl)
        } else {
            //更新
            dao.update(scoreTbl).toLong()
        }
    }


}

