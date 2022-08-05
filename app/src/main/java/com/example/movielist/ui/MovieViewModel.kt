package com.example.movielist.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.movielist.network.Movie
import com.example.movielist.network.MovieApi
import com.example.movielist.repository.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(api = MovieApi)

    val movies = repository.movies

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean> = _isNetworkErrorShown

    init {
        getMovieListFromRepository()
    }

    private fun getMovieListFromRepository() {
        viewModelScope.launch {
            try {
                repository.getMovieList()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun onMovieClicked(movie: Movie) {
        _movie.value = movie
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}