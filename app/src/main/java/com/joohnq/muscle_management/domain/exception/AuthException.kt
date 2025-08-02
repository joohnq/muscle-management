package com.joohnq.muscle_management.domain.exception

sealed class AuthException(message: String) : Exception(message) {
    class TooManyRequestsException : AuthException("Muitas tentativas. Tente novamente mais tarde.")
    class InvalidCredentialsException : AuthException("Credenciais inválidas. Verifique seu e-mail e senha.")
    class GenericAuthException : AuthException("Ocorreu um erro ao autenticar.")
    class ApiNotAvailableException : AuthException("API de autenticação não disponível.")
    class InvalidEmailException : AuthException("E-mail inválido.")
    class InvalidUserException : AuthException("Usuário não encontrado.")
    class UserCollisionException : AuthException("Já existe uma conta com este e-mail.")
    class WeakPasswordException : AuthException("Senha fraca. Escolha uma mais segura.")
    class NetworkException : AuthException("Sem conexão com a internet.")
}