package com.example.moviehub.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    launchSingleTop = true
                }
            },
            icon = {
                Text("⌂")
            },
            label = {
                Text("Početna")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "movies",
            onClick = {
                navController.navigate("movies") {
                    launchSingleTop = true
                }
            },
            icon = {
                Text("🎬")
            },
            label = {
                Text("Filmovi")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "chat",
            onClick = {
                navController.navigate("chat") {
                    launchSingleTop = true
                }
            },
            icon = {
                Text("💬")
            },
            label = {
                Text("Chat")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                }
            },
            icon = {
                Text("👤")
            },
            label = {
                Text("Profil")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "settings",
            onClick = {
                navController.navigate("settings") {
                    launchSingleTop = true
                }
            },
            icon = {
                Text("⚙")
            },
            label = {
                Text("Postavke")
            }
        )
    }
}