package com.eltex.androidschool.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eltex.androidschool.dao.EventDao
import com.eltex.androidschool.entity.EventEntity

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE Events_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                author TEXT NOT NULL,
                content TEXT NOT NULL,
                date TEXT NOT NULL,
                likedByMe INTEGER NOT NULL,
                status TEXT NOT NULL,
                statusTime TEXT NOT NULL,
                link TEXT NOT NULL,
                participants TEXT NOT NULL,
                participatedByMe INTEGER NOT NULL
            )
        """.trimIndent())

        db.execSQL("""
            INSERT INTO Events_new (id, author, content, date, likedByMe, status, statusTime, link, participants, participatedByMe)
            SELECT id, author, content, date, likedByMe, status, statusTime, link, participants, participatedByMe FROM Events
        """.trimIndent())

        db.execSQL("DROP TABLE Events")

        db.execSQL("ALTER TABLE Events_new RENAME TO Events")
    }
}

@Database(
    entities = [EventEntity::class],
    version = 3
)

abstract class AppDb : RoomDatabase() {
    abstract val eventDao: EventDao

    companion object {

        @Volatile
        private var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb {
            INSTANCE?.let { return it }

            val application = context.applicationContext

            synchronized(this) {
                INSTANCE?.let { return it }

                val appDb = Room.databaseBuilder(application, AppDb::class.java, "appdb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .createFromAsset("database/appdb.db")
                    .addMigrations(MIGRATION_2_3)
                    .build()

                INSTANCE = appDb

                return appDb
            }
        }
    }
}

