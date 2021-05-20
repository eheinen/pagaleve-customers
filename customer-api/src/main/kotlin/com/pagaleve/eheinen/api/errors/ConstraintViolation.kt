package com.pagaleve.eheinen.api.errors

data class ConstraintViolation(
    val field: String,
    val constraint: String,
    val invalidValue: String,
    val message: String
)
