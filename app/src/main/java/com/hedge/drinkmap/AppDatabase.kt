package com.hedge.drinkmap

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hedge.drinkmap.data.models.DrinkEntity
import com.hedge.drinkmap.domain.DrinkDao

@Database(entities = arrayOf(DrinkEntity::class),version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun drinkDao():DrinkDao

    companion object {
        private var INSTANCE:AppDatabase? = null

        fun getDatabase(context:Context):AppDatabase{
            INSTANCE = INSTANCE?: Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"drink_db").build()
            return INSTANCE!!
        }
    }
}