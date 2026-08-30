package com.example.moviehub.ui.movies

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.moviehub.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    val context = LocalContext.current
    val uiState by movieViewModel.uiState.collectAsState()

    val movie = uiState.movies.find { it.id == movieId }

    if (movie == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Film nije pronađen.",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text("Nazad")
                }
            }
        }

        return
    }

    val imageResourceId = context.resources.getIdentifier(
        movie.imageUrl,
        "drawable",
        context.packageName
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 90.dp
                ),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (imageResourceId != 0) {
                    AsyncImage(
                        model = imageResourceId,
                        contentDescription = "Poster filma ${movie.title}",
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .fillMaxWidth()
                            .height(400.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "${movie.releaseYear} • ${movie.genre}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "⭐ ${movie.rating}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = movie.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
        }

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Nazad")
        }
    }
}