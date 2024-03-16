package com.example.dogsandddapters.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.base.MyApplication

@Database(entities = [PersonPost::class], version = 7)
abstract class AppLocalDbPPRepository : RoomDatabase() {
    abstract fun PersonPostsDao(): PersonPostsDao
}

object AppLocalDatabasePersonPost {
    val db: AppLocalDbPPRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbPPRepository::class.java,
            "PersonPostFileName.db"
        )
            .addMigrations(migration_5_6)
            .fallbackToDestructiveMigration()
            .build()
    }

    private val migration_5_6 = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Perform migration logic here
        }
    }
}