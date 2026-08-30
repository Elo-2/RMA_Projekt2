package com.example.moviehub

import com.example.moviehub.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieSearchTest {

    private val movies = listOf(
        Movie(
            id = 1,
            title = "Inception",
            description = "A thief who steals secrets through dreams.",
            imageUrl = "inception.jpg",
            rating = 8.8,
            releaseYear = 2010,
            genre = "Sci-Fi"
        ),
        Movie(
            id = 2,
            title = "The Dark Knight",
            description = "Batman faces a criminal mastermind.",
            imageUrl = "dark_knight.jpg",
            rating = 9.0,
            releaseYear = 2008,
            genre = "Action"
        ),
        Movie(
            id = 3,
            title = "Interstellar",
            description = "Explorers travel through a wormhole.",
            imageUrl = "interstellar.jpg",
            rating = 8.7,
            releaseYear = 2014,
            genre = "Sci-Fi"
        )
    )

    private fun searchMovies(query: String): List<Movie> {
        if (query.isBlank()) {
            return movies
        }

        return movies.filter { movie ->
            movie.title.contains(
                query.trim(),
                ignoreCase = true
            )
        }
    }

    @Test
    fun searchByTitle_returnsMatchingMovie() {
        val result = searchMovies("Inception")

        assertEquals(1, result.size)
        assertEquals("Inception", result.first().title)
    }

    @Test
    fun searchIsCaseInsensitive() {
        val result = searchMovies("inception")

        assertEquals(1, result.size)
        assertEquals("Inception", result.first().title)
    }

    @Test
    fun searchWithUppercase_returnsMatchingMovie() {
        val result = searchMovies("INCEPTION")

        assertEquals(1, result.size)
        assertEquals("Inception", result.first().title)
    }

    @Test
    fun searchWithMixedCase_returnsMatchingMovie() {
        val result = searchMovies("iNcEpTiOn")

        assertEquals(1, result.size)
        assertEquals("Inception", result.first().title)
    }

    @Test
    fun searchWithNoMatch_returnsEmptyList() {
        val result = searchMovies("Avatar")

        assertTrue(result.isEmpty())
    }

    @Test
    fun searchWithRandomText_returnsEmptyList() {
        val result = searchMovies("XYZ123")

        assertTrue(result.isEmpty())
    }

    @Test
    fun blankSearch_returnsAllMovies() {
        val result = searchMovies("")

        assertEquals(3, result.size)
    }

    @Test
    fun spacesOnlySearch_returnsAllMovies() {
        val result = searchMovies("     ")

        assertEquals(3, result.size)
    }

    @Test
    fun searchWithSpaces_ignoresExtraSpaces() {
        val result = searchMovies("  Inception  ")

        assertEquals(1, result.size)
        assertEquals("Inception", result.first().title)
    }

    @Test
    fun searchWithLeadingSpaces_works() {
        val result = searchMovies("  Inception")

        assertEquals(1, result.size)
    }

    @Test
    fun searchWithTrailingSpaces_works() {
        val result = searchMovies("Inception  ")

        assertEquals(1, result.size)
    }

    @Test
    fun partialTitle_returnsMatchingMovie() {
        val result = searchMovies("cept")

        assertEquals(1, result.size)
        assertEquals("Inception", result.first().title)
    }

    @Test
    fun partialTitleBeginning_returnsMatchingMovie() {
        val result = searchMovies("Inter")

        assertEquals(1, result.size)
        assertEquals("Interstellar", result.first().title)
    }

    @Test
    fun partialTitleMiddle_returnsMatchingMovie() {
        val result = searchMovies("Dark")

        assertEquals(1, result.size)
        assertEquals("The Dark Knight", result.first().title)
    }

    @Test
    fun searchThe_returnsDarkKnight() {
        val result = searchMovies("The")

        assertEquals(1, result.size)
        assertEquals("The Dark Knight", result.first().title)
    }

    @Test
    fun searchSciFiTitlePart_returnsCorrectMovie() {
        val result = searchMovies("stellar")

        assertEquals(1, result.size)
        assertEquals("Interstellar", result.first().title)
    }

    @Test
    fun searchDoesNotMatchDescription() {
        val result = searchMovies("Batman")

        assertTrue(result.isEmpty())
    }

    @Test
    fun searchDoesNotMatchGenre() {
        val result = searchMovies("Sci-Fi")

        assertTrue(result.isEmpty())
    }

    @Test
    fun searchDoesNotMatchImageUrl() {
        val result = searchMovies("inception.jpg")

        assertTrue(result.isEmpty())
    }

    @Test
    fun searchResultContainsCorrectId() {
        val result = searchMovies("Inception")

        assertEquals(1, result.first().id)
    }

    @Test
    fun searchResultContainsCorrectRating() {
        val result = searchMovies("Inception")

        assertEquals(8.8, result.first().rating, 0.0)
    }

    @Test
    fun searchResultContainsCorrectReleaseYear() {
        val result = searchMovies("Inception")

        assertEquals(2010, result.first().releaseYear)
    }

    @Test
    fun searchResultContainsCorrectGenre() {
        val result = searchMovies("Inception")

        assertEquals("Sci-Fi", result.first().genre)
    }

    @Test
    fun searchResultPreservesMovieOrder() {
        val result = searchMovies("")

        assertEquals("Inception", result[0].title)
        assertEquals("The Dark Knight", result[1].title)
        assertEquals("Interstellar", result[2].title)
    }

    @Test
    fun searchResultDoesNotModifyOriginalList() {
        val result = searchMovies("Inception")

        assertEquals(3, movies.size)
        assertEquals(1, result.size)
    }
}