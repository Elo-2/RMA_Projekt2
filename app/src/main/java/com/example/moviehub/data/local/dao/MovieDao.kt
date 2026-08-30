package com.example.moviehub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviehub.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query(
        """
        SELECT * FROM movies
        WHERE title LIKE '%' || :query || '%'
        OR genre LIKE '%' || :query || '%'
        """
    )
    fun searchMovies(query: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}