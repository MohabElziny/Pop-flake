package com.iti.android.zoz.pop_flake.data.repository

import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.datasource.local.ILocalDataSource
import com.iti.android.zoz.pop_flake.data.datasource.remote.IRemoteDataSource
import com.iti.android.zoz.pop_flake.data.pojos.*
import com.iti.android.zoz.pop_flake.utils.CONNECTION_FAILURE
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IRepository {
    override suspend fun getComingSoonMovies(): NetworkResponse<List<Movie>> {
        return try {
            val response = remoteDataSource.getComingSoonMovies()
            if (response.isSuccessful) {
                if (response.body()?.movies.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    NetworkResponse.SuccessResponse(response.body()?.movies ?: emptyList())
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override suspend fun getInTheatersMovies(): NetworkResponse<List<Movie>> {
        return try {
            val response = remoteDataSource.getInTheatersMovies()
            if (response.isSuccessful) {
                if (response.body()?.movies.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    NetworkResponse.SuccessResponse(response.body()?.movies ?: emptyList())
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override suspend fun getTopRatedMovies(): NetworkResponse<List<TopMovie>> {
        return try {
            val response = remoteDataSource.getTopRatedMovies()
            if (response.isSuccessful) {
                if (response.body()?.topMovies.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    NetworkResponse.SuccessResponse(response.body()?.topMovies ?: emptyList())
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override suspend fun getBoxOfficeMovies(): NetworkResponse<List<BoxOfficeMovie>> {
        return try {
            val response = remoteDataSource.getBoxOfficeMovies()
            if (response.isSuccessful) {
                if (response.body()?.boxOfficeMovies.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    NetworkResponse.SuccessResponse(response.body()?.boxOfficeMovies ?: emptyList())
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override suspend fun getMostPopularMovies(): NetworkResponse<List<MostPopularMovie>> {
        return try {
            val response = remoteDataSource.getMostPopularMovies()
            if (response.isSuccessful) {
                if (response.body()?.mostPopularMovies.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    NetworkResponse.SuccessResponse(
                        response.body()?.mostPopularMovies ?: emptyList()
                    )
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override suspend fun getMoviePoster(movie_id: String): NetworkResponse<Poster> {
        return try {
            val response = remoteDataSource.getMoviePoster(movie_id)
            if (response.isSuccessful) {
                if (response.body()?.posters.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    val poster = response.body()?.posters?.get(0)
                    poster?.id = response.body()?.id ?: ""
                    NetworkResponse.SuccessResponse(
                        poster ?: Poster("", "")
                    )
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override suspend fun getMovieTrailer(movie_id: String): NetworkResponse<MovieTrailer> {
        return try {
            val response = remoteDataSource.getMovieTrailer(movie_id)
            if (response.isSuccessful) {
                if (!response.body()?.errorMessage.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    NetworkResponse.SuccessResponse(
                        response.body() ?: MovieTrailer("", "", "", "")
                    )
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override suspend fun webSearchQuery(query: String): NetworkResponse<List<SearchResult>> {
        return try {
            val response = remoteDataSource.webSearchQuery(query.replace(" ", "%"))
            if (response.isSuccessful) {
                if (response.body()?.results.isNullOrEmpty()) {
                    NetworkResponse.FailureResponse(response.body()?.errorMessage.toString())
                } else {
                    NetworkResponse.SuccessResponse(response.body()?.results ?: emptyList())
                }
            } else {
                NetworkResponse.FailureResponse(response.errorBody().toString())
            }
        } catch (ex: Exception) {
            NetworkResponse.FailureResponse(CONNECTION_FAILURE)
        }
    }

    override fun getThemeMode(): Int = localDataSource.getThemeMode()

    override fun setThemeMode(themeMode: Int) = localDataSource.setThemeMode(themeMode)
}
