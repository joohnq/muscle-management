package com.joohnq.muscle_management.domain.exception

class ValidationException(val errors: List<Exception>) : Exception()