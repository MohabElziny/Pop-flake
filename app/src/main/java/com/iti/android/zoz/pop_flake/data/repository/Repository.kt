package com.iti.android.zoz.pop_flake.data.repository

import com.iti.android.zoz.pop_flake.data.NetworkResponse
import com.iti.android.zoz.pop_flake.data.pojos.BoxOfficeMovie
import com.iti.android.zoz.pop_flake.data.pojos.Movie
import com.iti.android.zoz.pop_flake.data.pojos.TopMovie
import com.iti.android.zoz.pop_flake.data.remotedatasource.IRemoteDataSource
import javax.inject.Inject

private const val connectionFailure = "Bad Connection"

class Repository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource
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
            NetworkResponse.FailureResponse(connectionFailure)
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
            NetworkResponse.FailureResponse(connectionFailure)
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
            NetworkResponse.FailureResponse(connectionFailure)
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
            NetworkResponse.FailureResponse(connectionFailure)
        }
    }
}
