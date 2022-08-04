package com.iti.android.zoz.pop_flake.domain

import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.pojos.Poster

interface IPostersUseCase {
    suspend fun getMoviesPosters(): NetworkResponse<List<Poster>>
}