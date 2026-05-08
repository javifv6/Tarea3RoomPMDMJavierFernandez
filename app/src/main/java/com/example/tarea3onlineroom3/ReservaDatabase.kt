package com.example.tarea3onlineroom3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reserva::class], version = 1, exportSchema = false)
abstract class ReservaDatabase : RoomDatabase() {

    abstract fun reservaDao(): ReservaDao

    companion object {
        @Volatile
        private var INSTANCE: ReservaDatabase? = null

        fun getDatabase(context: Context): ReservaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReservaDatabase::class.java,
                    "hipica_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}