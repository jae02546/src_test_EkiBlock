package io.github.jae02546.test_ekiblock

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

//dbを2つに分けようかと思ったがうまくいかない?のでやめ

@Database(
    entities = [FolderTbl::class, FolderInfoTbl::class, QuestionTbl::class, AnswerTbl::class, FastestTbl::class, FolderExpandTbl::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NarabeDatabase : RoomDatabase() {

    abstract fun folderDao(): FolderDao
    abstract fun folderInfoDao(): FolderInfoDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
    abstract fun fastestDao(): FastestDao
    abstract fun folderExpandDao(): FolderExpandDao

    companion object {

        private var INSTANCE: NarabeDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): NarabeDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NarabeDatabase::class.java, "narabe.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}

//@Database(
//    entities = [FolderTbl::class, FolderInfoTbl::class, QuestionTbl::class],
//    version = 1, exportSchema = false
//)
//@TypeConverters(Converters::class)
//abstract class QuestionDatabase : RoomDatabase() {
//
//    abstract fun folderDao(): FolderDao
//    abstract fun folderInfoDao(): FolderInfoDao
//    abstract fun questionDao(): QuestionDao
//
//    companion object {
//
//        private var INSTANCE: QuestionDatabase? = null
//
//        private val lock = Any()
//
//        fun getInstance(context: Context): QuestionDatabase {
//            synchronized(lock) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        QuestionDatabase::class.java, "Question.db"
//                    )
//                        .allowMainThreadQueries()
//                        .build()
//                }
//                return INSTANCE!!
//            }
//        }
//    }
//}
//
//@Database(
//    entities = [PlayerRecordTbl::class, RecordTbl::class, FolderExpandTbl::class],
//    version = 1, exportSchema = false
//)
//@TypeConverters(Converters::class)
//abstract class AnswerDatabase : RoomDatabase() {
//
//    abstract fun playerRecordDao(): PlayerRecordDao
//    abstract fun recordDao(): RecordDao
//    abstract fun folderExpandDao(): FolderExpandDao
//
//    companion object {
//
//        private var INSTANCE: AnswerDatabase? = null
//
//        private val lock = Any()
//
//        fun getInstance(context: Context): AnswerDatabase {
//            synchronized(lock) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        AnswerDatabase::class.java, "Answer.db"
//                    )
//                        .allowMainThreadQueries()
//                        .build()
//                }
//                return INSTANCE!!
//            }
//        }
//    }
//}

private class Converters {
    private val intListType: Type = object : TypeToken<MutableList<Int>>() {}.type

    @TypeConverter
    fun fromIntListJson(intListJson: String): List<Int> =
        Gson().fromJson(intListJson, intListType)

    @TypeConverter
    fun intListToJson(intList: List<Int>): String = Gson().toJson(intList)

    private val questionItemListType: Type = object : TypeToken<MutableList<QuestionItemTbl>>() {}.type

    @TypeConverter
    fun fromQuestionItemListJson(questionItemListJson: String): List<QuestionItemTbl> =
        Gson().fromJson(questionItemListJson, questionItemListType)

    @TypeConverter
    fun questionItemListToJson(questionItemList: List<QuestionItemTbl>): String = Gson().toJson(questionItemList)

    @TypeConverter
    fun fromDuration(charSequence: String): Duration =
        Duration.parse(charSequence)

    @TypeConverter
    fun durationToCharSequence(duration: Duration): String =
        duration.toString()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
    @TypeConverter
    fun toLocalDateTime(dateTimeText: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeText) // Default format is ISO_LOCAL_DATE_TIME
    }
}

