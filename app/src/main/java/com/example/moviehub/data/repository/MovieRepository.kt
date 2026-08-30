package com.example.moviehub.data.repository

import com.example.moviehub.data.local.dao.MovieDao
import com.example.moviehub.data.local.toMovie
import com.example.moviehub.data.local.toMovieEntity
import com.example.moviehub.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val movieDao: MovieDao
) {

    fun getMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { movies ->
            movies.map { movie ->
                movie.toMovie()
            }
        }
    }

    fun searchMovies(query: String): Flow<List<Movie>> {
        return movieDao.searchMovies(query).map { movies ->
            movies.map { movie ->
                movie.toMovie()
            }
        }
    }

    suspend fun getMovieById(id: Int): Movie? {
        return movieDao.getMovieById(id)?.toMovie()
    }

    suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insertMovies(
            movies.map { movie ->
                movie.toMovieEntity()
            }
        )
    }

    suspend fun deleteAllMovies() {
        movieDao.deleteAllMovies()
    }
}