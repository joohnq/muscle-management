package com.joohnq.muscle_management.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joohnq.muscle_management.domain.entity.TopLevelRoute
import com.joohnq.muscle_management.ui.exercise.ExerciseScreen
import com.joohnq.muscle_management.ui.navigation.Destination
import com.joohnq.muscle_management.ui.training.TrainingScreen

@Composable
fun MuscleManagement() {
    val navController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute("Training", Destination.Training, Icons.Default.Home),
        TopLevelRoute("Exercise", Destination.Exercise, Icons.Default.Build)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val hierarchy = currentDestination?.hierarchy
                val isSelected =
                    { topLevelRoute: TopLevelRoute -> hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true }

                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = isSelected(topLevelRoute),
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = Destination.Training) {
            composable<Destination.Training> {
                TrainingScreen(
                    padding = padding,
                    onNavigateToAddTraining = {}
                )
            }
            composable<Destination.Exercise> {
                ExerciseScreen(
                    padding = padding,
                    onNavigateToAddExercise = {}
                )
            }
        }
    }
}