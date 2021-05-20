package com.pagaleve.eheinen.api.customer

import java.math.BigDecimal
import java.time.Instant

data class CustomerDto(
    val cpf: String,
    val email: String,
    val phoneNumber: String,
    val name: String,
    val birthdate: String,
    val profession: String,
    val salary: BigDecimal,

    val country: String,
    val state: String,
    val city: String,
    val zipcode: String,
    val street: String,
    val number: String,
    val complement: String,

    val createdAt: Instant
)
