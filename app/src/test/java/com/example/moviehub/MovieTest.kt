package com.example.moviehub

import com.example.moviehub.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieTest {

    private fun createMovie(): Movie {
        return Movie(
            id = 1,
            title = "Inception",
            description = "Test description",
            imageUrl = "test.jpg",
            rating = 8.8,
            releaseYear = 2010,
            genre = "Sci-Fi"
        )
    }

    @Test
    fun movie_hasCorrectId() {
        val movie = createMovie()

        assertEquals(1, movie.id)
    }

    @Test
    fun movie_hasCorrectTitle() {
        val movie = createMovie()

        assertEquals("Inception", movie.title)
    }

    @Test
    fun movie_hasCorrectDescription() {
        val movie = createMovie()

        assertEquals("Test description", movie.description)
    }

    @Test
    fun movie_hasCorrectImageUrl() {
        val movie = createMovie()

        assertEquals("test.jpg", movie.imageUrl)
    }

    @Test
    fun movie_hasCorrectRating() {
        val movie = createMovie()

        assertEquals(8.8, movie.rating, 0.0)
    }

    @Test
    fun movie_hasCorrectReleaseYear() {
        val movie = createMovie()

        assertEquals(2010, movie.releaseYear)
    }

    @Test
    fun movie_hasCorrectGenre() {
        val movie = createMovie()

        assertEquals("Sci-Fi", movie.genre)
    }

    @Test
    fun movie_canHaveDifferentId() {
        val movie = createMovie().copy(id = 99)

        assertEquals(99, movie.id)
    }

    @Test
    fun movie_canHaveDifferentTitle() {
        val movie = createMovie().copy(title = "Interstellar")

        assertEquals("Interstellar", movie.title)
    }

    @Test
    fun movie_canHaveDifferentDescription() {
        val movie = createMovie().copy(
            description = "A space adventure."
        )

        assertEquals("A space adventure.", movie.description)
    }

    @Test
    fun movie_canHaveDifferentImageUrl() {
        val movie = createMovie().copy(
            imageUrl = "new_image.jpg"
        )

        assertEquals("new_image.jpg", movie.imageUrl)
    }

    @Test
    fun movie_canHaveDifferentRating() {
        val movie = createMovie().copy(rating = 9.5)

        assertEquals(9.5, movie.rating, 0.0)
    }

    @Test
    fun movie_canHaveDifferentReleaseYear() {
        val movie = createMovie().copy(releaseYear = 2020)

        assertEquals(2020, movie.releaseYear)
    }

    @Test
    fun movie_canHaveDifferentGenre() {
        val movie = createMovie().copy(genre = "Drama")

        assertEquals("Drama", movie.genre)
    }

    @Test
    fun movieCopy_preservesOriginalMovie() {
        val original = createMovie()
        val copied = original.copy(title = "Changed")

        assertEquals("Inception", original.title)
        assertEquals("Changed", copied.title)
    }

    @Test
    fun movieCopy_preservesOtherFields() {
        val original = createMovie()
        val copied = original.copy(title = "Changed")

        assertEquals(original.id, copied.id)
        assertEquals(original.description, copied.description)
        assertEquals(original.imageUrl, copied.imageUrl)
        assertEquals(original.rating, copied.rating, 0.0)
        assertEquals(original.releaseYear, copied.releaseYear)
        assertEquals(original.genre, copied.genre)
    }

    @Test
    fun identicalMovies_areEqual() {
        val first = createMovie()
        val second = createMovie()

        assertEquals(first, second)
    }

    @Test
    fun differentMovies_areNotEqual() {
        val first = createMovie()
        val second = createMovie().copy(id = 2)

        assertNotEquals(first, second)
    }

    @Test
    fun differentTitles_makeMoviesDifferent() {
        val first = createMovie()
        val second = createMovie().copy(title = "Matrix")

        assertNotEquals(first, second)
    }

    @Test
    fun differentRatings_makeMoviesDifferent() {
        val first = createMovie()
        val second = createMovie().copy(rating = 7.5)

        assertNotEquals(first, second)
    }

    @Test
    fun differentGenres_makeMoviesDifferent() {
        val first = createMovie()
        val second = createMovie().copy(genre = "Drama")

        assertNotEquals(first, second)
    }

    @Test
    fun movie_hasNonEmptyTitle() {
        val movie = createMovie()

        assertTrue(movie.title.isNotEmpty())
    }

    @Test
    fun movie_hasNonEmptyDescription() {
        val movie = createMovie()

        assertTrue(movie.description.isNotEmpty())
    }

    @Test
    fun movie_hasNonEmptyImageUrl() {
        val movie = createMovie()

        assertTrue(movie.imageUrl.isNotEmpty())
    }

    @Test
    fun movie_hasNonEmptyGenre() {
        val movie = createMovie()

        assertTrue(movie.genre.isNotEmpty())
    }

    @Test
    fun movie_ratingIsPositive() {
        val movie = createMovie()

        assertTrue(movie.rating > 0)
    }

    @Test
    fun movie_releaseYearIsPositive() {
        val movie = createMovie()

        assertTrue(movie.releaseYear > 0)
    }

    @Test
    fun movie_idIsPositive() {
        val movie = createMovie()

        assertTrue(movie.id > 0)
    }

    @Test
    fun movie_canHaveZeroRating() {
        val movie = createMovie().copy(rating = 0.0)

        assertEquals(0.0, movie.rating, 0.0)
    }

    @Test
    fun movie_canHaveTenRating() {
        val movie = createMovie().copy(rating = 10.0)

        assertEquals(10.0, movie.rating, 0.0)
    }

    @Test
    fun movie_canHaveCurrentReleaseYear() {
        val movie = createMovie().copy(releaseYear = 2026)

        assertEquals(2026, movie.releaseYear)
    }

    @Test
    fun movie_canHaveEmptyDescription() {
        val movie = createMovie().copy(description = "")

        assertEquals("", movie.description)
    }

    @Test
    fun movie_canHaveEmptyImageUrl() {
        val movie = createMovie().copy(imageUrl = "")

        assertEquals("", movie.imageUrl)
    }

    @Test
    fun movie_canHaveEmptyGenre() {
        val movie = createMovie().copy(genre = "")

        assertEquals("", movie.genre)
    }

    @Test
    fun movie_canHaveLongTitle() {
        val title = "A".repeat(100)
        val movie = createMovie().copy(title = title)

        assertEquals(title, movie.title)
    }

    @Test
    fun movie_toStringContainsTitle() {
        val movie = createMovie()

        assertTrue(movie.toString().contains("Inception"))
    }

    @Test
    fun movie_toStringContainsGenre() {
        val movie = createMovie()

        assertTrue(movie.toString().contains("Sci-Fi"))
    }

    @Test
    fun movie_isNotNull() {
        val movie = createMovie()

        assertNotNull(movie)
    }

    @Test
    fun movieCopy_canChangeMultipleFields() {
        val movie = createMovie().copy(
            id = 10,
            title = "Interstellar",
            rating = 9.1,
            releaseYear = 2014,
            genre = "Adventure"
        )

        assertEquals(10, movie.id)
        assertEquals("Interstellar", movie.title)
        assertEquals(9.1, movie.rating, 0.0)
        assertEquals(2014, movie.releaseYear)
        assertEquals("Adventure", movie.genre)
    }

    @Test
    fun originalMovie_remainsUnchangedAfterCopy() {
        val original = createMovie()

        original.copy(
            id = 50,
            title = "Changed",
            rating = 5.0
        )

        assertEquals(1, original.id)
        assertEquals("Inception", original.title)
        assertEquals(8.8, original.rating, 0.0)
    }

    @Test
    fun moviesWithSameValues_haveSameHashCode() {
        val first = createMovie()
        val second = createMovie()

        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun moviesWithDifferentIds_haveDifferentValues() {
        val first = createMovie()
        val second = createMovie().copy(id = 2)

        assertFalse(first == second)
    }
}