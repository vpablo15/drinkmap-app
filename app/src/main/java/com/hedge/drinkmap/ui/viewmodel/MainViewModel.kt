package com.hedge.drinkmap.ui.viewmodel

import androidx.lifecycle.*
import com.hedge.drinkmap.data.models.Drink
import com.hedge.drinkmap.data.models.DrinkEntity
import com.hedge.drinkmap.domain.Repo
import com.hedge.drinkmap.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo:Repo):ViewModel() {

    private val drinkData = MutableLiveData<String>()

    fun setDrink(drinkName:String){
        drinkData.value = drinkName
    }

    init {
        setDrink("Margarita")
    }

    val fetchDrinksList = drinkData.distinctUntilChanged().switchMap { drinkName ->
        liveData<Resource<List<Drink>>>(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getDrinksList(drinkName))
            }catch (e:Exception){
                emit(Resource.Failure(e))
            }
        }
    }

    fun getFavDrinks() = liveData<Resource<List<DrinkEntity>>>(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getFavsDrinks())
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

    fun saveDrink(drink:DrinkEntity){
        viewModelScope.launch {
            repo.insertDrink(drink)
        }
    }

    fun deleteDrink(drink: DrinkEntity){
        viewModelScope.launch {
            repo.deleteDrink(drink)
        }
    }

}