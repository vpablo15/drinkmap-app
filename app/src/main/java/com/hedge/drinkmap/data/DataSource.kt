package com.hedge.drinkmap.data

import com.hedge.drinkmap.AppDatabase
import com.hedge.drinkmap.data.models.Drink
import com.hedge.drinkmap.data.models.DrinkEntity
import com.hedge.drinkmap.vo.Resource
import com.hedge.drinkmap.vo.RetrofitClient

class DataSource(private val appDatabase: AppDatabase) {

    suspend fun getDrinkByName(drinkName:String):Resource<List<Drink>>{
        return Resource.Success(RetrofitClient.webservice.getDrinkByName(drinkName).drinkList)
    }

    suspend fun getFavDrinks():Resource<List<DrinkEntity>>{
        return Resource.Success(appDatabase.drinkDao().getAllFavDrinks())
    }

    suspend fun insertDrinkRoom(drink:DrinkEntity){
        appDatabase.drinkDao().insertDrink(drink)
    }

    suspend fun deleteDrinkRoom(drink: DrinkEntity) {
        appDatabase.drinkDao().deleteDrink(drink)
    }

}