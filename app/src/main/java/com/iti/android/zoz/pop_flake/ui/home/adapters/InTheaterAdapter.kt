package com.iti.android.zoz.pop_flake.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.android.zoz.pop_flake.databinding.FilmWithRatingCardBinding
import com.iti.android.zoz.pop_flake.pojos.Movie

class InTheaterAdapter : RecyclerView.Adapter<InTheaterAdapter.InTheaterViewHolder>() {

    private var inTheaterMovies: List<Movie> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setInTheaterMoviesList(inTheaterMovies: List<Movie>) {
        this.inTheaterMovies = inTheaterMovies
        notifyDataSetChanged()
    }

    inner class InTheaterViewHolder(private val ratingFilmBinding: FilmWithRatingCardBinding) :
        RecyclerView.ViewHolder(ratingFilmBinding.root) {
        private val inTheaterMovie get() = inTheaterMovies[bindingAdapterPosition]

        fun bindData() {
            ratingFilmBinding.apply {
                txtRating.text = inTheaterMovie.imDbRating
                txtFilmDate.text = inTheaterMovie.year
                txtFilmName.text = inTheaterMovie.title
                Glide.with(root.context)
                    .load(inTheaterMovie.image)
                    .into(imgFilm)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTheaterViewHolder =
        InTheaterViewHolder(
            FilmWithRatingCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: InTheaterViewHolder, position: Int) = holder.bindData()

    override fun getItemCount(): Int = inTheaterMovies.size
}