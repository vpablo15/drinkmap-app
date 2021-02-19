package com.hedge.drinkmap.ui

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hedge.drinkmap.AppDatabase
import com.hedge.drinkmap.R
import com.hedge.drinkmap.data.DataSource
import com.hedge.drinkmap.data.models.Drink
import com.hedge.drinkmap.data.models.DrinkEntity
import com.hedge.drinkmap.databinding.FragmentDetailsBinding
import com.hedge.drinkmap.databinding.FragmentMainBinding
import com.hedge.drinkmap.domain.RepoImpl
import com.hedge.drinkmap.ui.viewmodel.MainViewModel
import com.hedge.drinkmap.ui.viewmodel.VMFactory
import com.hedge.drinkmap.vo.Resource


class DetailsFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel> {
        VMFactory(RepoImpl(DataSource(AppDatabase.getDatabase(requireActivity().applicationContext))))
    }

    private lateinit var binding: FragmentDetailsBinding

    private lateinit var drink: Drink

    private var fav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            drink = it.getParcelable("drink")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI(){
        val navController = findNavController()
        with(binding){
            imgBackButton.setOnClickListener { navController.navigateUp() }
            Glide.with(requireContext()).load(drink.image).centerCrop().into(imageViewDetails)
            textViewDetails.text = drink.name
            textViewDescription.text = drink.description
            findDrink(drink)
            imgFav.setOnClickListener {
                setFavImageAction()
            }
        }
    }

    private fun findDrink(drink:Drink){
        viewModel.getFavDrinks().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val list = it.data.map { each ->
                        Drink(each.drinkId,each.image,each.name,each.description)
                    }
                    fav = list.contains(drink)
                    if(fav) animationAdd()
                }
                is Resource.Failure -> {}
            }
        })
    }

    private fun setFavImageAction(){
       if(!fav){
           animationAdd()
           saveDrink()
       }else{
           animationRemove()
           deleteDrink()
       }
       fav = !fav
    }

    private fun deleteDrink(){
        viewModel.deleteDrink(DrinkEntity(
                drink.id,
                drink.image,
                drink.name,
                drink.description))
    }

    private fun saveDrink(){
        viewModel.saveDrink(DrinkEntity(
            drink.id,
            drink.image,
            drink.name,
            drink.description))
    }

    private fun animationAdd(){
        binding.imgFav.setAnimation(R.raw.favheart5)
        binding.imgFav.playAnimation()
    }

    private fun animationRemove(){
        binding.imgFav.animate().alpha(0f).setDuration(200)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    binding.imgFav.setImageResource(R.drawable.ic_launcher_heart4_foreground)
                    binding.imgFav.alpha = 1f
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            })
    }
}