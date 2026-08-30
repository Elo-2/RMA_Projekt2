package com.example.moviehub.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviehub.ui.MainScreen
import com.example.moviehub.ui.auth.LoginScreen
import com.example.moviehub.ui.auth.RegisterScreen
import com.example.moviehub.ui.chat.ChatScreen
import com.example.moviehub.ui.home.HomeScreen
import com.example.moviehub.ui.movies.MovieDetailScreen
import com.example.moviehub.ui.movies.MoviesScreen
import com.example.moviehub.ui.profile.ProfileScreen
import com.example.moviehub.ui.settings.SettingsScreen
import com.example.moviehub.ui.splash.SplashScreen
import com.example.moviehub.viewmodel.MovieViewModel
import com.example.moviehub.viewmodel.SettingsViewModel

@Composable
fun MovieHubApp(
    settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()

    val context = LocalContext.current

    val movieViewModel: MovieViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            context.applicationContext as Application
        )
    )

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("home") {
            MainScreen(
                navController = navController
            ) {
                HomeScreen(
                    navController = navController,
                    movieViewModel = movieViewModel
                )
            }
        }

        composable("movies") {
            MainScreen(
                navController = navController
            ) {
                MoviesScreen(
                    navController = navController,
                    movieViewModel = movieViewModel
                )
            }
        }

        composable(
            route = "movie/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val movieId = backStackEntry
                .arguments
                ?.getInt("movieId")
                ?: return@composable

            MovieDetailScreen(
                movieId = movieId,
                navController = navController,
                movieViewModel = movieViewModel
            )
        }

        composable("chat") {
            MainScreen(
                navController = navController
            ) {
                ChatScreen(navController)
            }
        }

        composable("profile") {
            MainScreen(
                navController = navController
            ) {
                ProfileScreen(navController)
            }
        }

        composable("settings") {
            MainScreen(
                navController = navController
            ) {
                SettingsScreen(
                    navController = navController,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}