package com.example.moviehub

import com.example.moviehub.model.Movie
import com.example.moviehub.viewmodel.MovieUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieUiStateBehaviorTest {

    private val movie = Movie(
        id = 1,
        title = "Inception",
        description = "A thief who steals secrets through dreams.",
        imageUrl = "inception.jpg",
        rating = 8.8,
        releaseYear = 2010,
        genre = "Sci-Fi"
    )

    @Test
    fun stateCanContainOneMovie() {
        val state = MovieUiState(
            movies = listOf(movie)
        )

        assertEquals(1, state.movies.size)
        assertEquals("Inception", state.movies.first().title)
    }

    @Test
    fun stateCanContainMultipleMovies() {
        val secondMovie = movie.copy(
            id = 2,
            title = "The Dark Knight"
        )

        val state = MovieUiState(
            movies = listOf(
                movie,
                secondMovie
            )
        )

        assertEquals(2, state.movies.size)
    }

    @Test
    fun stateCanContainThreeMovies() {
        val secondMovie = movie.copy(
            id = 2,
            title = "The Dark Knight"
        )

        val thirdMovie = movie.copy(
            id = 3,
            title = "Interstellar"
        )

        val state = MovieUiState(
            movies = listOf(
                movie,
                secondMovie,
                thirdMovie
            )
        )

        assertEquals(3, state.movies.size)
    }

    @Test
    fun stateCanContainEmptyMovieList() {
        val state = MovieUiState(
            movies = emptyList()
        )

        assertTrue(state.movies.isEmpty())
    }

    @Test
    fun searchQueryCanBeChanged() {
        val state = MovieUiState(
            searchQuery = "Inception"
        )

        assertEquals("Inception", state.searchQuery)
    }

    @Test
    fun searchQueryCanBeEmpty() {
        val state = MovieUiState(
            searchQuery = ""
        )

        assertEquals("", state.searchQuery)
    }

    @Test
    fun searchQueryCanContainSpaces() {
        val state = MovieUiState(
            searchQuery = "The Dark Knight"
        )

        assertEquals("The Dark Knight", state.searchQuery)
    }

    @Test
    fun loadingStateCanBeEnabled() {
        val state = MovieUiState(
            isLoading = true
        )

        assertTrue(state.isLoading)
    }

    @Test
    fun loadingStateCanBeDisabled() {
        val state = MovieUiState(
            isLoading = false
        )

        assertFalse(state.isLoading)
    }

    @Test
    fun stateCanContainMovieAndSearchQuery() {
        val state = MovieUiState(
            movies = listOf(movie),
            searchQuery = "Inception",
            isLoading = false
        )

        assertEquals(1, state.movies.size)
        assertEquals("Inception", state.searchQuery)
        assertFalse(state.isLoading)
    }

    @Test
    fun stateCanContainMoviesWhileLoading() {
        val state = MovieUiState(
            movies = listOf(movie),
            isLoading = true
        )

        assertEquals(1, state.movies.size)
        assertTrue(state.isLoading)
    }

    @Test
    fun stateCanContainSearchQueryWhileLoading() {
        val state = MovieUiState(
            searchQuery = "Inception",
            isLoading = true
        )

        assertEquals("Inception", state.searchQuery)
        assertTrue(state.isLoading)
    }

    @Test
    fun copyCanUpdateSearchQuery() {
        val initialState = MovieUiState()

        val updatedState = initialState.copy(
            searchQuery = "Matrix"
        )

        assertEquals("", initialState.searchQuery)
        assertEquals("Matrix", updatedState.searchQuery)
    }

    @Test
    fun copyCanUpdateLoadingState() {
        val initialState = MovieUiState()

        val updatedState = initialState.copy(
            isLoading = true
        )

        assertFalse(initialState.isLoading)
        assertTrue(updatedState.isLoading)
    }

    @Test
    fun copyCanUpdateMovies() {
        val initialState = MovieUiState()

        val updatedState = initialState.copy(
            movies = listOf(movie)
        )

        assertTrue(initialState.movies.isEmpty())
        assertEquals(1, updatedState.movies.size)
    }

    @Test
    fun copyCanUpdateAllStateValues() {
        val initialState = MovieUiState()

        val updatedState = initialState.copy(
            movies = listOf(movie),
            searchQuery = "Inception",
            isLoading = true
        )

        assertEquals(1, updatedState.movies.size)
        assertEquals("Inception", updatedState.searchQuery)
        assertTrue(updatedState.isLoading)
    }

    @Test
    fun copyPreservesMoviesWhenOnlySearchQueryChanges() {
        val initialState = MovieUiState(
            movies = listOf(movie),
            searchQuery = "",
            isLoading = false
        )

        val updatedState = initialState.copy(
            searchQuery = "Inception"
        )

        assertEquals(initialState.movies, updatedState.movies)
        assertEquals("Inception", updatedState.searchQuery)
        assertFalse(updatedState.isLoading)
    }

    @Test
    fun copyPreservesSearchQueryWhenOnlyLoadingChanges() {
        val initialState = MovieUiState(
            movies = listOf(movie),
            searchQuery = "Inception",
            isLoading = false
        )

        val updatedState = initialState.copy(
            isLoading = true
        )

        assertEquals("Inception", updatedState.searchQuery)
        assertEquals(initialState.movies, updatedState.movies)
        assertTrue(updatedState.isLoading)
    }

    @Test
    fun copyCanClearMovies() {
        val initialState = MovieUiState(
            movies = listOf(movie)
        )

        val updatedState = initialState.copy(
            movies = emptyList()
        )

        assertEquals(1, initialState.movies.size)
        assertTrue(updatedState.movies.isEmpty())
    }

    @Test
    fun copyCanClearSearchQuery() {
        val initialState = MovieUiState(
            searchQuery = "Inception"
        )

        val updatedState = initialState.copy(
            searchQuery = ""
        )

        assertEquals("Inception", initialState.searchQuery)
        assertEquals("", updatedState.searchQuery)
    }

    @Test
    fun copyCanStopLoading() {
        val initialState = MovieUiState(
            isLoading = true
        )

        val updatedState = initialState.copy(
            isLoading = false
        )

        assertTrue(initialState.isLoading)
        assertFalse(updatedState.isLoading)
    }

    @Test
    fun movieCopyPreservesOriginalMovieData() {
        val changedMovie = movie.copy(
            title = "Changed Title"
        )

        assertEquals("Inception", movie.title)
        assertEquals("Changed Title", changedMovie.title)
        assertEquals(movie.id, changedMovie.id)
        assertEquals(movie.rating, changedMovie.rating, 0.0)
    }

    @Test
    fun movieCopyCanChangeId() {
        val changedMovie = movie.copy(
            id = 10
        )

        assertEquals(1, movie.id)
        assertEquals(10, changedMovie.id)
    }

    @Test
    fun movieCopyCanChangeRating() {
        val changedMovie = movie.copy(
            rating = 9.5
        )

        assertEquals(8.8, movie.rating, 0.0)
        assertEquals(9.5, changedMovie.rating, 0.0)
    }

    @Test
    fun movieCopyCanChangeGenre() {
        val changedMovie = movie.copy(
            genre = "Action"
        )

        assertEquals("Sci-Fi", movie.genre)
        assertEquals("Action", changedMovie.genre)
    }

    @Test
    fun movieCopyCanChangeReleaseYear() {
        val changedMovie = movie.copy(
            releaseYear = 2020
        )

        assertEquals(2010, movie.releaseYear)
        assertEquals(2020, changedMovie.releaseYear)
    }

    @Test
    fun movieCopyCanChangeDescription() {
        val changedMovie = movie.copy(
            description = "New description"
        )

        assertEquals(
            "A thief who steals secrets through dreams.",
            movie.description
        )
        assertEquals(
            "New description",
            changedMovie.description
        )
    }

    @Test
    fun movieCopyCanChangeImageUrl() {
        val changedMovie = movie.copy(
            imageUrl = "new.jpg"
        )

        assertEquals("inception.jpg", movie.imageUrl)
        assertEquals("new.jpg", changedMovie.imageUrl)
    }
}