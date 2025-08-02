package com.joohnq.muscle_management.di

import com.joohnq.muscle_management.ui.auth.sign_in.SignInViewModel
import com.joohnq.muscle_management.ui.auth.sign_up.SignUpViewModel
import com.joohnq.muscle_management.ui.splash.SplashViewModel
import com.joohnq.muscle_management.ui.training.add.AddTrainingViewModel
import com.joohnq.muscle_management.ui.training.edit.EditTrainingViewModel
import com.joohnq.muscle_management.ui.training.overview.TrainingOverviewViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel {
        SplashViewModel(
            getUserIdUseCase = get(),
        )
    }
    viewModel {
        SignInViewModel(
            signInUseCase = get(),
        )
    }
    viewModel {
        SignUpViewModel(
            signUpUseCase = get(),
        )
    }
    viewModel {
        TrainingOverviewViewModel(
            getAllTrainingUseCase = get(),
            signOutUseCase = get(),
            deleteTrainingUseCase = get(),
        )
    }
    viewModel {
        AddTrainingViewModel(
            addTrainingUseCase = get(),
        )
    }
    viewModel {
        EditTrainingViewModel(
            getByIdTrainingUseCase = get(),
            updateTrainingUseCase = get(),
        )
    }
}