package com.joohnq.muscle_management.di

import com.joohnq.muscle_management.domain.use_case.training.AddTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.DeleteTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.GetAllTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.GetByIdTrainingUseCase
import com.joohnq.muscle_management.domain.use_case.training.UpdateTrainingUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule: Module = module {
    singleOf(::AddTrainingUseCase)
    singleOf(::GetAllTrainingUseCase)
    singleOf(::DeleteTrainingUseCase)
    singleOf(::GetByIdTrainingUseCase)
    singleOf(::UpdateTrainingUseCase)
}