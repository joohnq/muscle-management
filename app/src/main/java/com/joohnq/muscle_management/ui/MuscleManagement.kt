package com.joohnq.muscle_management.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.joohnq.muscle_management.ui.auth.sign_in.SignInScreen
import com.joohnq.muscle_management.ui.auth.sign_up.SignUpScreen
import com.joohnq.muscle_management.ui.navigation.Destination
import com.joohnq.muscle_management.ui.splash.SplashScreen
import com.joohnq.muscle_management.ui.training.add.AddTrainingScreen
import com.joohnq.muscle_management.ui.training.edit.EditTrainingScreen
import com.joohnq.muscle_management.ui.training.overview.TrainingOverviewScreen

fun NavHostController.replace(route: Any) {
    navigate(route) {
        popUpTo(currentDestination?.id ?: graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateWithCleanStack(route: Any) {
    navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

@Composable
fun MuscleManagement() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.Splash) {
        composable<Destination.Splash> {
            SplashScreen(
                navigateToSignIn = {
                    navController.navigateWithCleanStack(Destination.SignIn)
                },
                navigateToTrainingOverview = {
                    navController.navigateWithCleanStack(Destination.TrainingOverview)
                },
            )
        }
        composable<Destination.SignIn> {
            SignInScreen(
                navigateNext = {
                    navController.navigateWithCleanStack(Destination.TrainingOverview)
                },
                navigateSignUp = {
                    navController.replace(Destination.SignUp)
                },
            )
        }
        composable<Destination.SignUp> {
            SignUpScreen(
                navigateNext = {
                    navController.navigateWithCleanStack(Destination.TrainingOverview)
                },
                navigateSignIn = {
                    navController.replace(Destination.SignIn)
                },
            )
        }
        composable<Destination.TrainingOverview> {
            TrainingOverviewScreen(
                onNavigateToEditTraining = { id ->
                    navController.navigate(Destination.EditTraining(id))
                },
                onNavigateToAddTraining = {
                    navController.navigate(Destination.AddTraining)
                },
                onNavigateToSignIn = {
                    navController.navigateWithCleanStack(Destination.SignIn)
                },
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