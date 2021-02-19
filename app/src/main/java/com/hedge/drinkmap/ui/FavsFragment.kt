package com.hedge.drinkmap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hedge.drinkmap.AppDatabase
import com.hedge.drinkmap.R
import com.hedge.drinkmap.data.DataSource
import com.hedge.drinkmap.data.models.Drink
import com.hedge.drinkmap.databinding.FragmentFavsBinding
import com.hedge.drinkmap.domain.RepoImpl
import com.hedge.drinkmap.ui.viewmodel.MainViewModel
import com.hedge.drinkmap.ui.viewmodel.VMFactory
import com.hedge.drinkmap.vo.Resource


class FavsFragment : Fragment(),MainAdapter.OnDrinkClikListener {

    private val viewModel by viewModels<MainViewModel> {
        VMFactory(RepoImpl(DataSource(AppDatabase.getDatabase(requireActivity().applicationContext))))
    }

    private lateinit var binding: FragmentFavsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupFavBack()
    }

    private fun setupObservers(){
        viewModel.getFavDrinks().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val list = it.data.map { each ->
                        Drink(each.drinkId,each.image,each.name,each.description)
                    }
                    binding.recyclerViewFavs.adapter = MainAdapter(requireContext(),list,this)
                }
                is Resource.Failure -> {}
            }
        })
    }



    private fun setupRecyclerView(){
        binding.recyclerViewFavs.layoutManager = GridLayoutManager(requireContext(),2)
    }

    private fun setupFavBack(){
        binding.imgFavBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDrinkClick(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        findNavController().navigate(R.id.action_favsFragment_to_detailsFragment, bundle)

    }

}