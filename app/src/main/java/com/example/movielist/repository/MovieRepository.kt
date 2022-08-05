package com.example.movielist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movielist.network.Movie
import com.example.movielist.network.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val api: MovieApi) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    suspend fun getMovieList() {
        withContext(Dispatchers.IO) {
            val networkData = api.retrofitService.getMovies()
            _movies.postValue(networkData.items.sortedBy { it.releaseYear })
        }
    }

}