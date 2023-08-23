package com.alessandroorozco.evf4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.alessandroorozco.evf4.R
import com.squareup.picasso.Picasso

class BeerAdapter : RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {

    private var beers: List<Beer>? = null

    fun setData(beers: List<Beer>?) {
        this.beers = beers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false)
        return BeerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = beers?.get(position)
        holder.bind(beer)
    }

    override fun getItemCount(): Int {
        return beers?.size ?: 0
    }

    class BeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val beerImageView: ImageView = itemView.findViewById(R.id.beerImageView)

        fun bind(beer: Beer?) {
            idTextView.text = beer?.id.toString()
            nameTextView.text = beer?.name

            Picasso.get()
                .load(beer?.image_url)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_x)
                .fit()
                .centerCrop()
                .into(beerImageView)
        }
    }
}