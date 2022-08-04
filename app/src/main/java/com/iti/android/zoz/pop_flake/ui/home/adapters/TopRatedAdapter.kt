package com.iti.android.zoz.pop_flake.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.pojos.TopMovie
import com.iti.android.zoz.pop_flake.databinding.RatedMovieCardBinding

class TopRatedAdapter(private val showMovieDetails: (String) -> Unit) :
    RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>() {

    private var topRatedMovies: List<TopMovie> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setTopRatedMoviesList(topRatedMovies: List<TopMovie>) {
        this.topRatedMovies = topRatedMovies
        notifyDataSetChanged()
    }

    inner class TopRatedViewHolder(private val topRatedBinding: RatedMovieCardBinding) :
        RecyclerView.ViewHolder(topRatedBinding.root) {
        private val topRatedMovie get() = topRatedMovies[adapterPosition]

        init {
            topRatedBinding.movieCard.setOnClickListener {
                showMovieDetails(topRatedMovie.id)
            }
        }

        fun bindData() {
            topRatedBinding.apply {
                txtRating.text = topRatedMovie.imDbRating
                txtFilmName.text = topRatedMovie.title
                txtFilmDate.text =
                    root.context.getString(R.string.rank).plus(topRatedMovie.rank).plus(" ")
                        .plus(topRatedMovie.year)
                Glide.with(root.context)
                    .load(topRatedMovie.image)
                    .into(imgFilm)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder =
        TopRatedViewHolder(
            RatedMovieCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) = holder.bindData()

    override fun getItemCount(): Int = topRatedMovies.size
}