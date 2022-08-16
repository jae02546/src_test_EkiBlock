package io.github.jae02546.test_ekiblock

import androidx.room.*


@Dao
interface QuestionDao {
    //戻り値 RowId、-1なら失敗
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(questionTbl: QuestionTbl): Long

    //戻り値 update件数、0なら失敗
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(questionTbl: QuestionTbl): Int

    //戻り値 delete件数、0なら失敗
    @Delete
    fun delete(questionTbl: QuestionTbl): Int

    @Query("DELETE FROM QuestionTbl")
    fun deleteAll(): Int //これで件数返る? 返るようです

    @Query("SELECT * FROM QuestionTbl WHERE qNo = :qNo")
    fun getRecord(qNo: Int): MutableList<QuestionTbl>?

    @Query("SELECT * FROM QuestionTbl")
    fun getRecordAll(): MutableList<QuestionTbl>?
}

@Dao
interface AnswerDao {
    //戻り値 RowId、-1なら失敗
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(answerTbl: AnswerTbl): Long

    //戻り値 update件数、0なら失敗
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(answerTbl: AnswerTbl): Int

    //戻り値 delete件数、0なら失敗
    @Delete
    fun delete(answerTbl: AnswerTbl): Int

    @Query("SELECT * FROM AnswerTbl WHERE pNo = :pNo AND qNo = :qNo")
    fun getRecord(pNo: Int, qNo: Int): MutableList<AnswerTbl>?

    @Query("SELECT * FROM AnswerTbl")
    fun getRecordAll(): MutableList<AnswerTbl>?
}

@Dao
interface ScoreDao {
    //戻り値 RowId、-1なら失敗
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fastestTbl: ScoreTbl): Long

    //戻り値 update件数、0なら失敗
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(fastestTbl: ScoreTbl): Int

    //戻り値 delete件数、0なら失敗
    @Delete
    fun delete(fastestTbl: ScoreTbl): Int

    @Query("SELECT * FROM ScoreTbl WHERE pNo = :pNo")
    fun getRecord(pNo: Int): MutableList<ScoreTbl>?

    @Query("SELECT * FROM ScoreTbl")
    fun getRecordAll(): MutableList<ScoreTbl>?
}


