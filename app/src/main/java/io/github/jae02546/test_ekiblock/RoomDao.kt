package io.github.jae02546.test_ekiblock

import androidx.room.*

@Dao
interface FolderDao {
    //戻り値 RowId、-1なら失敗
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(folderTbl: FolderTbl): Long

    //戻り値 update件数、0なら失敗
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(folderTbl: FolderTbl): Int

    //戻り値 delete件数、0なら失敗
    @Delete
    fun delete(folderTbl: FolderTbl): Int

    @Query("DELETE FROM FolderTbl")
    fun deleteAll(): Int //これで件数返る? 返るようです

    @Query("SELECT * FROM FolderTbl WHERE fNo = :fNo")
    fun getRecord(fNo: Int): FolderTbl?

    @Query("SELECT * FROM FolderTbl ORDER BY fNo")
    fun getRecordAll(): MutableList<FolderTbl>?
}

@Dao
interface FolderInfoDao {
    //戻り値 RowId、-1なら失敗
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(folderInfoTbl: FolderInfoTbl): Long

    //戻り値 update件数、0なら失敗
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(folderInfoTbl: FolderInfoTbl): Int

    //戻り値 delete件数、0なら失敗
    @Delete
    fun delete(folderInfoTbl: FolderInfoTbl): Int

    @Query("DELETE FROM FolderInfoTbl")
    fun deleteAll(): Int //これで件数返る? 返るようです

    @Query("SELECT * FROM FolderInfoTbl WHERE fNo = :fNo")
    fun getRecord(fNo: Int): MutableList<FolderInfoTbl>?

    @Query("SELECT * FROM FolderInfoTbl")
    fun getRecordAll(): MutableList<FolderInfoTbl>?
}

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
interface FastestDao {
    //戻り値 RowId、-1なら失敗
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fastestTbl: FastestTbl): Long

    //戻り値 update件数、0なら失敗
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(fastestTbl: FastestTbl): Int

    //戻り値 delete件数、0なら失敗
    @Delete
    fun delete(fastestTbl: FastestTbl): Int

    @Query("SELECT * FROM FastestTbl WHERE qNo = :qNo")
    fun getRecord(qNo: Int): MutableList<FastestTbl>?

    @Query("SELECT * FROM FastestTbl")
    fun getRecordAll(): MutableList<FastestTbl>?
}

@Dao
interface FolderExpandDao {
    //戻り値 RowId、-1なら失敗
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(folderExpandTbl: FolderExpandTbl): Long

    //戻り値 update件数、0なら失敗
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(folderExpandTbl: FolderExpandTbl): Int

    //戻り値 delete件数、0なら失敗
    @Delete
    fun delete(folderExpandTbl: FolderExpandTbl): Int

    @Query("DELETE FROM FolderExpandTbl")
    fun deleteAll(): Int //これで件数返る?

    @Query("SELECT * FROM FolderExpandTbl WHERE fNo = :fNo")
    fun getRecord(fNo: Int): MutableList<FolderExpandTbl>?

    @Query("SELECT * FROM FolderExpandTbl")
    fun getRecordAll(): MutableList<FolderExpandTbl>?
}
