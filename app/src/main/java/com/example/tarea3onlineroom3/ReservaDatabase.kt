package com.example.tarea3onlineroom3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Clase principal de la base de datos de Room. 
 * Define las entidades y la versión, y proporciona acceso al DAO.
 */
@Database(entities = [Reserva::class], version = 2, exportSchema = false)
abstract class ReservaDatabase : RoomDatabase() {

    /** Proporciona acceso al DAO de Reservas */
    abstract fun reservaDao(): ReservaDao

    companion object {
        @Volatile
        private var INSTANCE: ReservaDatabase? = null

        /**
         * Obtiene la instancia única de la base de datos.
         * Si no existe, la crea de forma sincronizada para evitar múltiples instancias.
         */
        fun getDatabase(context: Context): ReservaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReservaDatabase::class.java,
                    "hipica_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}