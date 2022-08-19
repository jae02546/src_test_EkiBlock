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

    //問いNoリスト取得
    fun getQuestionNoList(context: Context): MutableList<Int> {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.questionDao()
        val rec = dao.getRecordAll() ?: mutableListOf()
        val qNoList: MutableList<Int> = mutableListOf()
        for (v in rec) {
            qNoList += v.qNo
        }
        return qNoList
    }

    //問いレコード取得
    fun getQuestionRecord(context: Context, qNo: Int): QuestionTbl? {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.questionDao()
        val rec = dao.getRecord(qNo) ?: mutableListOf()
        return if (rec.size > 0)
            rec[0]
        else
            null
    }

//    //問いmap取得
//    fun getQuestionMap(context: Context): MutableMap<Int, Tools.QuestionDc> {
//        val db = EkiWordDatabase.getInstance(context)
//        val dao = db.questionDao()
//        val rec = dao.getRecordAll() ?: mutableListOf()
//        val map: MutableMap<Int, Tools.QuestionDc> = mutableMapOf()
//        for (v in rec) {
//            val foo = Tools.QuestionDc(
//                v.name,
//                v.kana,
//                v.english,
//                v.info1,
//                v.info2,
//                v.info3,
//                v.url,
//                v.qiList,
//            )
//            map += v.qNo to foo
//        }
//        return map
//    }

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

    //ラスト状態List取得
    fun getLastStateList(context: Context): MutableList<LastStateTbl> {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.lastStateDao()
        return dao.getRecordAll() ?: mutableListOf()
    }

    //ラスト状態レコード取得
    fun getLastStateRecord(context: Context, pNo: Int): LastStateTbl? {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.lastStateDao()
        val rec = dao.getRecord(pNo) ?: mutableListOf()
        return if (rec.size > 0)
            rec[0]
        else
            null
    }

    //ラスト状態レコード書込
    fun putLastStateRecord(context: Context, lastStateTbl: LastStateTbl): Long {
        val db = EkiWordDatabase.getInstance(context)
        val dao = db.lastStateDao()
        val rec = dao.getRecord(lastStateTbl.pNo)
        return if (rec == null || rec.size <= 0) {
            //追加
            dao.insert(lastStateTbl)
        } else {
            //更新
            dao.update(lastStateTbl).toLong()
        }
    }


}

