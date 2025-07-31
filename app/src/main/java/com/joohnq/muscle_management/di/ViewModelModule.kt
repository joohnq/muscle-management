package com.joohnq.muscle_management.di

import com.joohnq.muscle_management.ui.training.add.AddTrainingViewModel
import com.joohnq.muscle_management.ui.training.edit.EditTrainingViewModel
import com.joohnq.muscle_management.ui.training.overview.TrainingOverviewViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel {
        TrainingOverviewViewModel(
            getAllTrainingUseCase = get(),
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