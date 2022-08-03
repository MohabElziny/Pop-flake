package com.iti.android.zoz.pop_flake.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.pojos.Movie
import com.iti.android.zoz.pop_flake.databinding.DefaultMovieCardBinding

class ComingSoonAdapter(private val showMovieDetails: (String) -> Unit) :
    RecyclerView.Adapter<ComingSoonAdapter.ComingSoonViewHolder>() {

    private var comingSoonMovies: List<Movie> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setComingSoonMoviesList(comingSoonMovies: List<Movie>) {
        this.comingSoonMovies = comingSoonMovies
        notifyDataSetChanged()
    }

    inner class ComingSoonViewHolder(private val comingSoonBinding: DefaultMovieCardBinding) :
        RecyclerView.ViewHolder(comingSoonBinding.root) {
        private val comingSoonMovie get() = comingSoonMovies[bindingAdapterPosition]

        init {
            comingSoonBinding.movieCard.setOnClickListener {
                showMovieDetails(comingSoonMovie.id)
            }
        }

        fun bindData() {
            comingSoonBinding.apply {
                txtFilmName.text = comingSoonMovie.title
                txtMoreDetails.text =
                    root.context.getString(R.string.release_date)
                        .plus(comingSoonMovie.releaseState)
                Glide.with(root.context)
                    .load(comingSoonMovie.image)
                    .into(imgFilm)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingSoonViewHolder =
        ComingSoonViewHolder(
            DefaultMovieCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: ComingSoonViewHolder, position: Int) = holder.bindData()

    override fun getItemCount(): Int = comingSoonMovies.size
}