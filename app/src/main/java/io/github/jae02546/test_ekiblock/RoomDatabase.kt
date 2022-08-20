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


@Database(
    entities = [QuestionTbl::class, ScoreTbl::class, LastStateTbl::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EkiWordDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun scoreDao(): ScoreDao
    abstract fun lastStateDao(): LastStateDao

    companion object {

        private var INSTANCE: EkiWordDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): EkiWordDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        EkiWordDatabase::class.java, "ekiWord.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}

private class Converters {

    //Int
    private val intListType: Type = object : TypeToken<MutableList<Int>>() {}.type

    @TypeConverter
    fun fromIntListJson(intListJson: String): List<Int> =
        Gson().fromJson(intListJson, intListType)

    @TypeConverter
    fun intListToJson(intList: List<Int>): String = Gson().toJson(intList)

    //String
    private val stringListType: Type = object : TypeToken<MutableList<String>>() {}.type

    @TypeConverter
    fun fromStringListJson(stringListJson: String): List<String> =
        Gson().fromJson(stringListJson, stringListType)

    @TypeConverter
    fun stringListToJson(stringList: List<String>): String = Gson().toJson(stringList)

    //List<String>
    private val stringListListType: Type =
        object : TypeToken<MutableList<MutableList<String>>>() {}.type

    @TypeConverter
    fun fromStringListListJson(stringListListJson: String): List<List<String>> =
        Gson().fromJson(stringListListJson, stringListListType)

    @TypeConverter
    fun stringListListToJson(stringListList: List<List<String>>): String = Gson().toJson(stringListList)

    //QuestionItemTbl
    private val questionItemListType: Type =
        object : TypeToken<MutableList<QuestionItemTbl>>() {}.type

    @TypeConverter
    fun fromQuestionItemListJson(questionItemListJson: String): List<QuestionItemTbl> =
        Gson().fromJson(questionItemListJson, questionItemListType)

    @TypeConverter
    fun questionItemListToJson(questionItemList: List<QuestionItemTbl>): String =
        Gson().toJson(questionItemList)

    //Duration
    @TypeConverter
    fun fromDuration(charSequence: String): Duration =
        Duration.parse(charSequence)

    @TypeConverter
    fun durationToCharSequence(duration: Duration): String =
        duration.toString()

    //Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    //LocalDateTime
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeText: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeText) // Default format is ISO_LOCAL_DATE_TIME
    }
}

