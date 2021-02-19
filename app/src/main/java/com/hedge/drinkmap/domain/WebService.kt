package com.hedge.drinkmap.domain

import com.hedge.drinkmap.data.models.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("search.php")
    suspend fun getDrinkByName(@Query("s")drinkName:String):DrinkList

}