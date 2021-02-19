package com.hedge.drinkmap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hedge.drinkmap.AppDatabase
import com.hedge.drinkmap.R
import com.hedge.drinkmap.data.DataSource
import com.hedge.drinkmap.data.models.Drink
import com.hedge.drinkmap.databinding.FragmentMainBinding
import com.hedge.drinkmap.domain.RepoImpl
import com.hedge.drinkmap.ui.viewmodel.MainViewModel
import com.hedge.drinkmap.ui.viewmodel.VMFactory
import com.hedge.drinkmap.vo.Resource

class MainFragment : Fragment(),MainAdapter.OnDrinkClikListener {

    private lateinit var binding:FragmentMainBinding

    private val viewModel by viewModels<MainViewModel> {
        VMFactory(RepoImpl(DataSource(AppDatabase.getDatabase(requireActivity().applicationContext))))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupObservers()
        setupImgFav()
    }

    override fun onDrinkClick(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable("drink",drink)
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment,bundle)
    }

    private fun setupSearchView(){
        binding.searchViewDrink.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setDrink(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupObservers(){
        viewModel.fetchDrinksList.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewDrinks.visibility = View.VISIBLE
                    if(result.data != null)
                        binding.recyclerViewDrinks.adapter = MainAdapter(requireContext(),result.data,this)
                    else
                        binding.recyclerViewDrinks.visibility = View.GONE
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"Failed to load data",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupRecyclerView(){
        binding.recyclerViewDrinks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDrinks.addItemDecoration(DividerItemDecoration(requireContext()
            ,DividerItemDecoration.VERTICAL))
    }

    private fun setupImgFav(){
        binding.imgFavs.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favsFragment)
        }
    }

}