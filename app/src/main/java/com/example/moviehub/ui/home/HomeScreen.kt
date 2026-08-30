package com.example.moviehub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.moviehub.viewmodel.MovieViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    val context = LocalContext.current

    val uiState by movieViewModel.uiState.collectAsState()

    /*
     * Od svih dostupnih filmova nasumično biramo 5.
     *
     * remember osigurava da se izbor ne mijenja
     * pri svakom ponovnom iscrtavanju ekrana.
     */
    val recommendedMovies = remember(uiState.movies) {
        uiState.movies
            .shuffled()
            .take(5)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "MovieHub",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Dobrodošli u MovieHub!",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Preporučeno za vas",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (recommendedMovies.isEmpty()) {

            Text(
                text = "Trenutno nema dostupnih filmova.",
                style = MaterialTheme.typography.bodyMedium
            )

        } else {

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                items(
                    items = recommendedMovies,
                    key = { movie ->
                        movie.id
                    }
                ) { movie ->

                    val imageResourceId =
                        context.resources.getIdentifier(
                            movie.imageUrl,
                            "drawable",
                            context.packageName
                        )

                    Card(
                        onClick = {
                            navController.navigate(
                                "movie/${movie.id}"
                            )
                        },
                        modifier = Modifier.width(240.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            /*
                             * Poster se učitava iz:
                             * app/src/main/res/drawable
                             */
                            if (imageResourceId != 0) {

                                AsyncImage(
                                    model = imageResourceId,
                                    contentDescription = "Poster filma ${movie.title}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(280.dp),
                                    contentScale = ContentScale.Fit
                                )

                                Spacer(
                                    modifier = Modifier.height(12.dp)
                                )
                            }

                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "${movie.releaseYear} • ${movie.genre}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "⭐ ${movie.rating}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Button(
                                onClick = {
                                    navController.navigate(
                                        "movie/${movie.id}"
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Pogledaj")
                            }
                        }
                    }
                }
            }
        }
    }
}