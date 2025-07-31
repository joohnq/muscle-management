package com.joohnq.muscle_management.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.joohnq.muscle_management.ui.navigation.Destination
import com.joohnq.muscle_management.ui.training.add.AddTrainingScreen
import com.joohnq.muscle_management.ui.training.edit.EditTrainingScreen
import com.joohnq.muscle_management.ui.training.overview.TrainingOverviewScreen

@Composable
fun MuscleManagement() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.TrainingOverview) {
        composable<Destination.TrainingOverview> {
            TrainingOverviewScreen(
                onEditTraining = { id ->
                    navController.navigate(Destination.EditTraining(id))
                },
                onNavigateToAddTraining = { navController.navigate(Destination.AddTraining) }
            )
        }
        composable<Destination.AddTraining> {
            AddTrainingScreen(
                onGoBack = { navController.popBackStack() },
            )
        }
        composable<Destination.EditTraining> {
            val args = it.toRoute<Destination.EditTraining>()

            EditTrainingScreen(
                id = args.id,
                onGoBack = { navController.popBackStack() },
            )
        }
    }
}