package com.alessandroorozco.evf4.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alessandroorozco.evf4.retrofit.ApiPunkService

import com.alessandroorozco.evf4.R
import com.alessandroorozco.evf4.adapter.Beer
import com.alessandroorozco.evf4.adapter.BeerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var beerAdapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        beerAdapter = BeerAdapter()
        recyclerView.adapter = beerAdapter


        val apiService = ApiPunkService.create()
        apiService.getBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                if (response.isSuccessful) {
                    val beers = response.body()
                    beerAdapter.setData(beers)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {

            }
        })

        return view
    }
}