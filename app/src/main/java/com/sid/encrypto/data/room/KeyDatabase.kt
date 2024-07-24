package com.sid.encrypto.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sid.encrypto.data.model.Key

@Database(entities = [Key::class],
    version = 3)
abstract class KeyDatabase : RoomDatabase() {

    abstract fun keyDao(): KeyDao

    companion object {
        @Volatile
        var INSTANCE : KeyDatabase? = null

        @Synchronized
        fun getDatabase(context: Context) : KeyDatabase {

            val tempInstance = INSTANCE

            if (tempInstance!=null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder (
                    context.applicationContext,
                    KeyDatabase::class.java,
                    "masterKey_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}