package com.iti.android.zoz.pop_flake.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.pojos.BoxOfficeMovie
import com.iti.android.zoz.pop_flake.databinding.DefaultMovieCardBinding

class BoxOfficeAdapter : RecyclerView.Adapter<BoxOfficeAdapter.BoxOfficeViewHolder>() {

    private var boxOfficeMovies: List<BoxOfficeMovie> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setBoxOfficeMoviesList(boxOfficeMovies: List<BoxOfficeMovie>) {
        this.boxOfficeMovies = boxOfficeMovies
        notifyDataSetChanged()
    }

    inner class BoxOfficeViewHolder(private val boxOfficeBinding: DefaultMovieCardBinding) :
        RecyclerView.ViewHolder(boxOfficeBinding.root) {
        private val boxOfficeMovie get() = boxOfficeMovies[bindingAdapterPosition]

        fun bindData() {
            boxOfficeBinding.apply {
                txtFilmName.text = boxOfficeMovie.title
                txtMoreDetails.text =
                    root.context.getString(R.string.rank).plus(boxOfficeMovie.rank)
                Glide.with(root.context)
                    .load(boxOfficeMovie.image)
                    .into(boxOfficeBinding.imgFilm)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxOfficeViewHolder =
        BoxOfficeViewHolder(
            DefaultMovieCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: BoxOfficeViewHolder, position: Int) = holder.bindData()

    override fun getItemCount(): Int = boxOfficeMovies.size
}