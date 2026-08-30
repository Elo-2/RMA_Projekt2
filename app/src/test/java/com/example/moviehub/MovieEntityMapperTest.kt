package com.example.moviehub

import com.example.moviehub.data.local.entity.MovieEntity
import com.example.moviehub.data.local.toMovie
import com.example.moviehub.data.local.toMovieEntity
import com.example.moviehub.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieEntityMapperTest {

    @Test
    fun movieEntity_toMovie_mapsId() {
        val entity = MovieEntity(id = 10)

        assertEquals(10, entity.toMovie().id)
    }

    @Test
    fun movieEntity_toMovie_mapsTitle() {
        val entity = MovieEntity(title = "Inception")

        assertEquals("Inception", entity.toMovie().title)
    }

    @Test
    fun movieEntity_toMovie_mapsDescription() {
        val entity = MovieEntity(description = "Test description")

        assertEquals("Test description", entity.toMovie().description)
    }

    @Test
    fun movieEntity_toMovie_mapsImageUrl() {
        val entity = MovieEntity(imageUrl = "movie.jpg")

        assertEquals("movie.jpg", entity.toMovie().imageUrl)
    }

    @Test
    fun movieEntity_toMovie_mapsRating() {
        val entity = MovieEntity(rating = 8.8)

        assertEquals(8.8, entity.toMovie().rating, 0.0)
    }

    @Test
    fun movieEntity_toMovie_mapsReleaseYear() {
        val entity = MovieEntity(releaseYear = 2010)

        assertEquals(2010, entity.toMovie().releaseYear)
    }

    @Test
    fun movieEntity_toMovie_mapsGenre() {
        val entity = MovieEntity(genre = "Sci-Fi")

        assertEquals("Sci-Fi", entity.toMovie().genre)
    }

    @Test
    fun movieEntity_toMovie_mapsAllFields() {
        val entity = MovieEntity(
            id = 1,
            title = "Inception",
            description = "Test description",
            imageUrl = "test.jpg",
            rating = 8.8,
            releaseYear = 2010,
            genre = "Sci-Fi"
        )

        val movie = entity.toMovie()

        assertEquals(entity.id, movie.id)
        assertEquals(entity.title, movie.title)
        assertEquals(entity.description, movie.description)
        assertEquals(entity.imageUrl, movie.imageUrl)
        assertEquals(entity.rating, movie.rating, 0.0)
        assertEquals(entity.releaseYear, movie.releaseYear)
        assertEquals(entity.genre, movie.genre)
    }

    @Test
    fun movie_toMovieEntity_mapsId() {
        val movie = Movie(id = 20)

        assertEquals(20, movie.toMovieEntity().id)
    }

    @Test
    fun movie_toMovieEntity_mapsTitle() {
        val movie = Movie(title = "Avatar")

        assertEquals("Avatar", movie.toMovieEntity().title)
    }

    @Test
    fun movie_toMovieEntity_mapsDescription() {
        val movie = Movie(description = "Movie description")

        assertEquals(
            "Movie description",
            movie.toMovieEntity().description
        )
    }

    @Test
    fun movie_toMovieEntity_mapsImageUrl() {
        val movie = Movie(imageUrl = "avatar.jpg")

        assertEquals(
            "avatar.jpg",
            movie.toMovieEntity().imageUrl
        )
    }

    @Test
    fun movie_toMovieEntity_mapsRating() {
        val movie = Movie(rating = 9.2)

        assertEquals(
            9.2,
            movie.toMovieEntity().rating,
            0.0
        )
    }

    @Test
    fun movie_toMovieEntity_mapsReleaseYear() {
        val movie = Movie(releaseYear = 2022)

        assertEquals(
            2022,
            movie.toMovieEntity().releaseYear
        )
    }

    @Test
    fun movie_toMovieEntity_mapsGenre() {
        val movie = Movie(genre = "Action")

        assertEquals(
            "Action",
            movie.toMovieEntity().genre
        )
    }

    @Test
    fun movie_toMovieEntity_mapsAllFields() {
        val movie = Movie(
            id = 2,
            title = "The Dark Knight",
            description = "Test description",
            imageUrl = "dark_knight.jpg",
            rating = 9.0,
            releaseYear = 2008,
            genre = "Action"
        )

        val entity = movie.toMovieEntity()

        assertEquals(movie.id, entity.id)
        assertEquals(movie.title, entity.title)
        assertEquals(movie.description, entity.description)
        assertEquals(movie.imageUrl, entity.imageUrl)
        assertEquals(movie.rating, entity.rating, 0.0)
        assertEquals(movie.releaseYear, entity.releaseYear)
        assertEquals(movie.genre, entity.genre)
    }

    @Test
    fun movieEntity_toMovie_preservesData() {
        val entity = MovieEntity(
            id = 10,
            title = "Interstellar",
            description = "Space movie",
            imageUrl = "interstellar.jpg",
            rating = 8.7,
            releaseYear = 2014,
            genre = "Sci-Fi"
        )

        val movie = entity.toMovie()

        assertEquals(
            Movie(
                id = 10,
                title = "Interstellar",
                description = "Space movie",
                imageUrl = "interstellar.jpg",
                rating = 8.7,
                releaseYear = 2014,
                genre = "Sci-Fi"
            ),
            movie
        )
    }

    @Test
    fun movie_toMovieEntity_preservesData() {
        val movie = Movie(
            id = 20,
            title = "The Matrix",
            description = "A science fiction movie",
            imageUrl = "matrix.jpg",
            rating = 8.7,
            releaseYear = 1999,
            genre = "Action"
        )

        val entity = movie.toMovieEntity()

        assertEquals(
            MovieEntity(
                id = 20,
                title = "The Matrix",
                description = "A science fiction movie",
                imageUrl = "matrix.jpg",
                rating = 8.7,
                releaseYear = 1999,
                genre = "Action"
            ),
            entity
        )
    }

    @Test
    fun emptyMovieEntity_mapsCorrectly() {
        val entity = MovieEntity(
            id = 0,
            title = "",
            description = "",
            imageUrl = "",
            rating = 0.0,
            releaseYear = 0,
            genre = ""
        )

        val movie = entity.toMovie()

        assertEquals(0, movie.id)
        assertEquals("", movie.title)
        assertEquals("", movie.description)
        assertEquals("", movie.imageUrl)
        assertEquals(0.0, movie.rating, 0.0)
        assertEquals(0, movie.releaseYear)
        assertEquals("", movie.genre)
    }

    @Test
    fun emptyMovie_mapsCorrectly() {
        val movie = Movie(
            id = 0,
            title = "",
            description = "",
            imageUrl = "",
            rating = 0.0,
            releaseYear = 0,
            genre = ""
        )

        val entity = movie.toMovieEntity()

        assertEquals(0, entity.id)
        assertEquals("", entity.title)
        assertEquals("", entity.description)
        assertEquals("", entity.imageUrl)
        assertEquals(0.0, entity.rating, 0.0)
        assertEquals(0, entity.releaseYear)
        assertEquals("", entity.genre)
    }

    @Test
    fun movieEntity_toMovie_doesNotChangeId() {
        val entity = MovieEntity(id = 999)

        assertEquals(999, entity.toMovie().id)
    }

    @Test
    fun movieEntity_toMovie_doesNotChangeRating() {
        val entity = MovieEntity(rating = 5.5)

        assertEquals(5.5, entity.toMovie().rating, 0.0)
    }

    @Test
    fun movieEntity_toMovie_doesNotChangeYear() {
        val entity = MovieEntity(releaseYear = 1999)

        assertEquals(1999, entity.toMovie().releaseYear)
    }

    @Test
    fun movieEntity_toMovie_doesNotChangeGenre() {
        val entity = MovieEntity(genre = "Comedy")

        assertEquals("Comedy", entity.toMovie().genre)
    }

    @Test
    fun movie_toMovieEntity_doesNotChangeId() {
        val movie = Movie(id = 500)

        assertEquals(500, movie.toMovieEntity().id)
    }

    @Test
    fun movie_toMovieEntity_doesNotChangeRating() {
        val movie = Movie(rating = 7.7)

        assertEquals(
            7.7,
            movie.toMovieEntity().rating,
            0.0
        )
    }

    @Test
    fun movie_toMovieEntity_doesNotChangeYear() {
        val movie = Movie(releaseYear = 2020)

        assertEquals(
            2020,
            movie.toMovieEntity().releaseYear
        )
    }

    @Test
    fun movie_toMovieEntity_doesNotChangeGenre() {
        val movie = Movie(genre = "Drama")

        assertEquals(
            "Drama",
            movie.toMovieEntity().genre
        )
    }

    @Test
    fun movieEntity_toMovie_returnsObject() {
        val entity = MovieEntity(title = "Test")

        assertNotNull(entity.toMovie())
    }

    @Test
    fun movie_toMovieEntity_returnsObject() {
        val movie = Movie(title = "Test")

        assertNotNull(movie.toMovieEntity())
    }

    @Test
    fun movieEntity_withDifferentTitles_producesDifferentMovies() {
        val first = MovieEntity(title = "Avatar").toMovie()
        val second = MovieEntity(title = "Titanic").toMovie()

        assertNotEquals(first, second)
    }

    @Test
    fun movie_withDifferentIds_producesDifferentEntities() {
        val first = Movie(id = 1).toMovieEntity()
        val second = Movie(id = 2).toMovieEntity()

        assertNotEquals(first, second)
    }

    @Test
    fun movieEntity_toMovie_preservesLongTitle() {
        val title = "A".repeat(100)

        val movie = MovieEntity(title = title).toMovie()

        assertEquals(title, movie.title)
        assertTrue(movie.title.length == 100)
    }
}