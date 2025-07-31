package com.joohnq.muscle_management.di

import com.joohnq.muscle_management.ui.training.TrainingViewModel
import com.joohnq.muscle_management.ui.training.add.AddTrainingViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModelOf(::TrainingViewModel)
    viewModel{
        AddTrainingViewModel(
            addTrainingUseCase = get(),
        )
    }
}