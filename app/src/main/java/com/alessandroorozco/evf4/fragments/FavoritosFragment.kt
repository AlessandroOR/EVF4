package com.alessandroorozco.evf4.fragments

import com.alessandroorozco.evf4.adapter.FavoriteAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.alessandroorozco.evf4.R
import com.alessandroorozco.evf4.adapter.Beer
import com.alessandroorozco.evf4.retrofit.ApiPunkService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoritosFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteBeers: MutableList<Beer> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)


        val favoriteRecyclerView: RecyclerView = view.findViewById(R.id.favoriteRecyclerView)
        favoriteAdapter = FavoriteAdapter()
        favoriteRecyclerView.adapter = favoriteAdapter
        favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        addBeerToFavorites(1)

        return view
    }

    private fun addBeerToFavorites(beerId: Int) {
        val apiService = ApiPunkService.create()
        apiService.getBeerById(beerId).enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                if (response.isSuccessful) {
                    val beers = response.body()
                    beers?.let {
                        if (beers.isNotEmpty()) {
                            favoriteBeers.add(beers[0])
                            favoriteAdapter.setData(favoriteBeers)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
            }
        })
    }
}