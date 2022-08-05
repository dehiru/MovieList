package com.example.movielist.network

import android.util.Log

data class MovieResponse(
    val items: List<Movie>
)

data class Movie(
    val title: String,
    val directorName: String,
    val releaseYear: Int,
    val actors: List<Actor>
) {

    fun getTitleAndYear(): String {
        return "$title ($releaseYear)"
    }

    fun getShortDirectorName(): String {
        var resultString = directorName.substringAfterLast(" ")
        resultString += " ${directorName.substringBefore(" ")[0]}."
        resultString += "${directorName.substringAfter(" ")[0]}."
        return resultString
    }

    fun getDistinctActors(): String {
        val actorNames = actors.map { it.actorName }
        return actorNames.toSet().joinToString(", ")
    }

}

data class Actor(
    val actorName: String
)