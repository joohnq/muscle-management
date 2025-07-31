package com.joohnq.muscle_management.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joohnq.muscle_management.ui.navigation.Destination
import com.joohnq.muscle_management.ui.training.TrainingScreen
import com.joohnq.muscle_management.ui.training.add.AddTrainingScreen

@Composable
fun MuscleManagement() {
    val navController = rememberNavController()

    Scaffold { padding ->
        NavHost(navController, startDestination = Destination.Training) {
            composable<Destination.Training> {
                TrainingScreen(
                    padding = padding,
                    onNavigateToAddTraining = { navController.navigate(Destination.AddTraining) }
                )
            }
            composable<Destination.AddTraining> {
                AddTrainingScreen(
                    padding = padding,
                    onGoBack = { navController.popBackStack() },
                )
            }
        }
    }
}