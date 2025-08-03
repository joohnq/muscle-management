package com.joohnq.muscle_management.di

import com.joohnq.muscle_management.domain.validator.EmailValidator
import com.joohnq.muscle_management.domain.validator.ExerciseValidator
import com.joohnq.muscle_management.domain.validator.PasswordValidator
import com.joohnq.muscle_management.domain.validator.TrainingValidator
import org.koin.core.module.Module
import org.koin.dsl.module

val validatorModule: Module = module {
    single { EmailValidator }
    single { PasswordValidator }
    single { TrainingValidator }
    single { ExerciseValidator }
}