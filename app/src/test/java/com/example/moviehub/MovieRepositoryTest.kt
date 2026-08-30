package com.example.moviehub

import com.example.moviehub.data.local.dao.MovieDao
import com.example.moviehub.data.local.entity.MovieEntity
import com.example.moviehub.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieRepositoryTest {

    @Test
    fun getMovies_returnsMappedMovies() = runBlocking {
        val dao = FakeMovieDao()

        dao.movies.value = listOf(
            MovieEntity(
                id = 1,
                title = "Inception",
                description = "Dream movie",
                imageUrl = "inception.jpg",
                rating = 8.8,
                releaseYear = 2010,
                genre = "Sci-Fi"
            )
        )

        val repository = MovieRepository(dao)

        val result = repository.getMovies().first()

        assertEquals(1, result.size)
        assertEquals(1, result.first().id)
        assertEquals("Inception", result.first().title)
        assertEquals("Dream movie", result.first().description)
        assertEquals("inception.jpg", result.first().imageUrl)
        assertEquals(8.8, result.first().rating, 0.0)
        assertEquals(2010, result.first().releaseYear)
        assertEquals("Sci-Fi", result.first().genre)
    }

    @Test
    fun getMovies_emptyDatabase_returnsEmptyList() = runBlocking {
        val dao = FakeMovieDao()
        val repository = MovieRepository(dao)

        val result = repository.getMovies().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun getMovies_returnsMultipleMovies() = runBlocking {
        val dao = FakeMovieDao()

        dao.movies.value = listOf(
            MovieEntity(
                id = 1,
                title = "Inception",
                description = "Dream movie",
                imageUrl = "1.jpg",
                rating = 8.8,
                releaseYear = 2010,
                genre = "Sci-Fi"
            ),
            MovieEntity(
                id = 2,
                title = "The Matrix",
                description = "Matrix movie",
                imageUrl = "2.jpg",
                rating = 8.7,
                releaseYear = 1999,
                genre = "Action"
            )
        )

        val repository = MovieRepository(dao)

        val result = repository.getMovies().first()

        assertEquals(2, result.size)
        assertEquals("Inception", result[0].title)
        assertEquals("The Matrix", result[1].title)
    }

    @Test
    fun searchMovies_returnsMappedSearchResults() = runBlocking {
        val dao = FakeMovieDao()

        dao.searchResults.value = listOf(
            MovieEntity(
                id = 1,
                title = "Inception",
                description = "Dream movie",
                imageUrl = "inception.jpg",
                rating = 8.8,
                releaseYear = 2010,
                genre = "Sci-Fi"
            )
        )

        val repository = MovieRepository(dao)

        val result = repository.searchMovies("Inception").first()

        assertEquals(1, result.size)
        assertEquals("Inception", result.first().title)
        assertEquals("Sci-Fi", result.first().genre)
    }

    @Test
    fun searchMovies_emptyResult_returnsEmptyList() = runBlocking {
        val dao = FakeMovieDao()
        val repository = MovieRepository(dao)

        val result = repository.searchMovies("Unknown").first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun searchMovies_returnsMultipleResults() = runBlocking {
        val dao = FakeMovieDao()

        dao.searchResults.value = listOf(
            MovieEntity(
                id = 1,
                title = "The Dark Knight",
                description = "Batman movie",
                imageUrl = "dark-knight.jpg",
                rating = 9.0,
                releaseYear = 2008,
                genre = "Action"
            ),
            MovieEntity(
                id = 2,
                title = "The Matrix",
                description = "Virtual reality movie",
                imageUrl = "matrix.jpg",
                rating = 8.7,
                releaseYear = 1999,
                genre = "Action"
            )
        )

        val repository = MovieRepository(dao)

        val result = repository.searchMovies("Action").first()

        assertEquals(2, result.size)
        assertEquals("The Dark Knight", result[0].title)
        assertEquals("The Matrix", result[1].title)
    }

    @Test
    fun getMovieById_existingMovie_returnsMovie() = runBlocking {
        val dao = FakeMovieDao()

        dao.moviesById[1] = MovieEntity(
            id = 1,
            title = "Inception",
            description = "Dream movie",
            imageUrl = "inception.jpg",
            rating = 8.8,
            releaseYear = 2010,
            genre = "Sci-Fi"
        )

        val repository = MovieRepository(dao)

        val result = repository.getMovieById(1)

        assertEquals(1, result?.id)
        assertEquals("Inception", result?.title)
        assertEquals("Sci-Fi", result?.genre)
    }

    @Test
    fun getMovieById_missingMovie_returnsNull() = runBlocking {
        val dao = FakeMovieDao()
        val repository = MovieRepository(dao)

        val result = repository.getMovieById(999)

        assertNull(result)
    }

    @Test
    fun insertMovies_convertsMoviesToEntities() = runBlocking {
        val dao = FakeMovieDao()
        val repository = MovieRepository(dao)

        val movies = listOf(
            com.example.moviehub.model.Movie(
                id = 1,
                title = "Inception",
                description = "Dream movie",
                imageUrl = "inception.jpg",
                rating = 8.8,
                releaseYear = 2010,
                genre = "Sci-Fi"
            ),
            com.example.moviehub.model.Movie(
                id = 2,
                title = "The Matrix",
                description = "Matrix movie",
                imageUrl = "matrix.jpg",
                rating = 8.7,
                releaseYear = 1999,
                genre = "Action"
            )
        )

        repository.insertMovies(movies)

        assertEquals(2, dao.insertedMovies.size)

        assertEquals(1, dao.insertedMovies[0].id)
        assertEquals("Inception", dao.insertedMovies[0].title)
        assertEquals(8.8, dao.insertedMovies[0].rating, 0.0)

        assertEquals(2, dao.insertedMovies[1].id)
        assertEquals("The Matrix", dao.insertedMovies[1].title)
        assertEquals("Action", dao.insertedMovies[1].genre)
    }

    @Test
    fun insertMovies_emptyList_passesEmptyListToDao() = runBlocking {
        val dao = FakeMovieDao()
        val repository = MovieRepository(dao)

        repository.insertMovies(emptyList())

        assertTrue(dao.insertedMovies.isEmpty())
    }

    @Test
    fun deleteAllMovies_callsDao() = runBlocking {
        val dao = FakeMovieDao()
        val repository = MovieRepository(dao)

        repository.deleteAllMovies()

        assertTrue(dao.deleteAllMoviesCalled)
    }

    @Test
    fun deleteAllMovies_canBeCalledMultipleTimes() = runBlocking {
        val dao = FakeMovieDao()
        val repository = MovieRepository(dao)

        repository.deleteAllMovies()
        repository.deleteAllMovies()

        assertEquals(2, dao.deleteAllMoviesCallCount)
    }
}

private class FakeMovieDao : MovieDao {

    val movies = MutableStateFlow<List<MovieEntity>>(emptyList())

    val searchResults = MutableStateFlow<List<MovieEntity>>(emptyList())

    val moviesById = mutableMapOf<Int, MovieEntity>()

    val insertedMovies = mutableListOf<MovieEntity>()

    var deleteAllMoviesCalled = false
    var deleteAllMoviesCallCount = 0

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return movies
    }

    override fun searchMovies(query: String): Flow<List<MovieEntity>> {
        return searchResults
    }

    override suspend fun getMovieById(id: Int): MovieEntity? {
        return moviesById[id]
    }

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        insertedMovies.clear()
        insertedMovies.addAll(movies)
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        moviesById.remove(movie.id)
    }

    override suspend fun deleteAllMovies() {
        deleteAllMoviesCalled = true
        deleteAllMoviesCallCount++
        movies.value = emptyList()
        moviesById.clear()
    }
}