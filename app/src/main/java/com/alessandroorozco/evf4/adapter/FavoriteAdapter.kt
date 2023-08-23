package com.alessandroorozco.evf4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.alessandroorozco.evf4.R
import com.squareup.picasso.Picasso

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteBeers: List<Beer> = emptyList()

    fun setData(favoriteBeers: List<Beer>) {
        this.favoriteBeers = favoriteBeers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_beer, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val beer = favoriteBeers[position]
        holder.bind(beer)
    }

    override fun getItemCount(): Int {
        return favoriteBeers.size
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val beerImageView: ImageView = itemView.findViewById(R.id.beerImageView)
        private val detailTextView: TextView = itemView.findViewById(R.id.detailTextView)

        fun bind(beer: Beer) {
            idTextView.text = beer.id.toString()
            nameTextView.text = beer.name
            detailTextView.text = beer.description

            Picasso.get()
                .load(beer.image_url)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_x)
                .fit()
                .centerCrop()
                .into(beerImageView)
        }
    }
}