package com.hedge.drinkmap.domain

import com.hedge.drinkmap.data.models.Drink
import com.hedge.drinkmap.data.models.DrinkEntity
import com.hedge.drinkmap.vo.Resource
import java.time.format.ResolverStyle

interface Repo {

    suspend fun getDrinksList(drinkName:String):Resource<List<Drink>>
    suspend fun getFavsDrinks():Resource<List<DrinkEntity>>
    suspend fun insertDrink(drink:DrinkEntity)
    suspend fun deleteDrink(drink: DrinkEntity)
}