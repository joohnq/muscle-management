package com.joohnq.muscle_management.di

import com.joohnq.muscle_management.data.repository.AuthRepositoryImpl
import com.joohnq.muscle_management.data.repository.TrainingRepositoryImpl
import com.joohnq.muscle_management.domain.repository.AuthRepository
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<TrainingRepository> { TrainingRepositoryImpl(get()) } bind TrainingRepository::class
    single<AuthRepository> { AuthRepositoryImpl(get()) } bind AuthRepository::class
}