package com.hedge.drinkmap.domain

import androidx.room.*
import com.hedge.drinkmap.data.models.DrinkEntity

@Dao
interface DrinkDao {

    @Query("SELECT * FROM drinkentity")
    suspend fun getAllFavDrinks():List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink:DrinkEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)
}