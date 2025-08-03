package com.joohnq.muscle_management.di

import com.joohnq.muscle_management.data.repository.AuthRepositoryImpl
import com.joohnq.muscle_management.data.repository.TrainingRepositoryImpl
import com.joohnq.muscle_management.domain.repository.AuthRepository
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            auth = get()
        )
    } bind AuthRepository::class
    single<TrainingRepository> {
        TrainingRepositoryImpl(
            firestore = get(),
            authRepository = get()
        )
    } bind TrainingRepository::class
}