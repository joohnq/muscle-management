package com.joohnq.muscle_management.ui

import android.app.Application
import com.google.firebase.FirebaseApp
import com.joohnq.muscle_management.di.firebaseModule
import com.joohnq.muscle_management.di.repositoryModule
import com.joohnq.muscle_management.di.useCaseModule
import com.joohnq.muscle_management.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MuscleManagementApp: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@MuscleManagementApp)
            modules(
                firebaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}