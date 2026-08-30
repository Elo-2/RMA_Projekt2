package com.example.moviehub.ui.movies

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.moviehub.viewmodel.MovieViewModel

@Composable
fun MoviesScreen(
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    val context = LocalContext.current
    val uiState by movieViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Filmovi",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = {
                movieViewModel.searchMovies(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Pretraži filmove")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = uiState.movies,
                key = { movie ->
                    movie.id
                }
            ) { movie ->

                val imageResourceId = context.resources.getIdentifier(
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {

                        if (imageResourceId != 0) {
                            AsyncImage(
                                model = imageResourceId,
                                contentDescription = "Poster filma ${movie.title}",
                                modifier = Modifier.size(
                                    width = 90.dp,
                                    height = 130.dp
                                ),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "${movie.releaseYear} • ${movie.genre}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "⭐ ${movie.rating}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = movie.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}