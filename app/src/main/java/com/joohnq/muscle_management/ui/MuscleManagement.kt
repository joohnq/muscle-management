package com.joohnq.muscle_management.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.joohnq.muscle_management.ui.navigation.Destination
import com.joohnq.muscle_management.ui.training.overview.TrainingOverviewScreen
import com.joohnq.muscle_management.ui.training.add.AddTrainingScreen
import com.joohnq.muscle_management.ui.training.edit.EditTrainingScreen

@Composable
fun MuscleManagement() {
    val navController = rememberNavController()

    Scaffold { padding ->
        NavHost(navController, startDestination = Destination.TrainingOverview) {
            composable<Destination.TrainingOverview> {
                TrainingOverviewScreen(
                    padding = padding,
                    onEditTraining = {
                        navController.navigate(Destination.EditTraining)
                    },
                    onNavigateToAddTraining = { navController.navigate(Destination.AddTraining) }
                )
            }
            composable<Destination.AddTraining> {
                AddTrainingScreen(
                    padding = padding,
                    onGoBack = { navController.popBackStack() },
                )
            }
            composable<Destination.EditTraining> {backStackEntry ->
                EditTrainingScreen(
                    padding = padding,
                    id = backStackEntry.toRoute<Destination.EditTraining>().id,
                    onGoBack = { navController.popBackStack() },
                )
            }
        }
    }
}