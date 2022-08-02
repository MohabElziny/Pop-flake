package com.iti.android.zoz.pop_flake.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.android.zoz.pop_flake.databinding.PosterTrailerLayoutBinding
import com.iti.android.zoz.pop_flake.data.pojos.Poster

class TrailerViewPagerAdapter : RecyclerView.Adapter<TrailerViewPagerAdapter.TrailerViewHolder>() {
    private var postersList: List<Poster> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setPostersList(postersList: List<Poster>) {
        this.postersList = postersList
        notifyDataSetChanged()
    }

    inner class TrailerViewHolder(private val posterTrailerBinding: PosterTrailerLayoutBinding) :
        RecyclerView.ViewHolder(posterTrailerBinding.root) {
        private val poster get() = postersList[bindingAdapterPosition]
        fun bindData() {
            Glide.with(posterTrailerBinding.root.context)
                .load(poster.image)
                .into(posterTrailerBinding.imgPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder =
        TrailerViewHolder(
            PosterTrailerLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) = holder.bindData()

    override fun getItemCount(): Int = postersList.size
}