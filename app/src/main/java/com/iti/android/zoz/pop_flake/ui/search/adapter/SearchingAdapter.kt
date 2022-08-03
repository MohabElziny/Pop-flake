package com.iti.android.zoz.pop_flake.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.android.zoz.pop_flake.data.pojos.SearchResult
import com.iti.android.zoz.pop_flake.databinding.DefaultMovieCardBinding


class SearchingAdapter : RecyclerView.Adapter<SearchingAdapter.SearchViewHolder>() {
    private var searchingList: List<SearchResult> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchingList(searchingList: List<SearchResult>) {
        this.searchingList = searchingList
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val searchBinding: DefaultMovieCardBinding) :
        RecyclerView.ViewHolder(searchBinding.root) {
        private val movie get() = searchingList[bindingAdapterPosition]
        fun bindData() {
            searchBinding.apply {
                txtFilmName.text = movie.title
                txtMoreDetails.text = movie.description
                Glide.with(root.context)
                    .load(movie.image)
                    .into(imgFilm)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            DefaultMovieCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bindData()

    override fun getItemCount(): Int = searchingList.size
}