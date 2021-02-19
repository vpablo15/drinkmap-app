package com.hedge.drinkmap.domain

import com.hedge.drinkmap.data.DataSource
import com.hedge.drinkmap.data.models.Drink
import com.hedge.drinkmap.data.models.DrinkEntity
import com.hedge.drinkmap.vo.Resource

class RepoImpl(private val dataSource: DataSource) : Repo {

    override suspend fun getDrinksList(drinkName:String): Resource<List<Drink>> {
        return dataSource.getDrinkByName(drinkName)
    }

    override suspend fun getFavsDrinks(): Resource<List<DrinkEntity>> {
        return dataSource.getFavDrinks()
    }

    override suspend fun insertDrink(drink: DrinkEntity) {
        dataSource.insertDrinkRoom(drink)
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        dataSource.deleteDrinkRoom(drink)
    }
}