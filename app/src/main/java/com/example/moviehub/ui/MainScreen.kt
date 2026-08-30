package com.example.moviehub.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.moviehub.navigation.BottomNavigationBar

@Composable
fun MainScreen(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) { paddingValues ->

        androidx.compose.foundation.layout.Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}