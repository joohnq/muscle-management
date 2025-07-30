package com.joohnq.muscle_management.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.Module
import org.koin.dsl.module

val firebaseModule: Module = module {
    single { FirebaseFirestore.getInstance() }
    single { Firebase.auth }
}