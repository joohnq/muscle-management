package com.joohnq.muscle_management.domain.mapper

import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.joohnq.muscle_management.domain.exception.AuthException
import com.joohnq.muscle_management.domain.exception.ValidationException

object AuthExceptionMapper {
    fun map(e: Exception): AuthException {
        return when (e) {
            is FirebaseTooManyRequestsException -> AuthException.TooManyRequestsException()
            is FirebaseAuthWeakPasswordException -> AuthException.WeakPasswordException()
            is FirebaseAuthInvalidCredentialsException -> AuthException.InvalidCredentialsException()
            is FirebaseApiNotAvailableException -> AuthException.ApiNotAvailableException()
            is FirebaseAuthEmailException -> AuthException.InvalidEmailException()
            is FirebaseAuthInvalidUserException -> AuthException.InvalidUserException()
            is FirebaseAuthUserCollisionException -> AuthException.UserCollisionException()
            is FirebaseNetworkException -> AuthException.NetworkException()
            is AuthException.EmptyPasswordException -> AuthException.EmptyPasswordException
            is AuthException.EmptyEmailException -> AuthException.EmptyEmailException
            is AuthException.InvalidEmailException -> AuthException.InvalidEmailException()
            is AuthException.UserNotLogged -> AuthException.UserNotLogged
            else -> AuthException.GenericAuthException()
        }
    }
}