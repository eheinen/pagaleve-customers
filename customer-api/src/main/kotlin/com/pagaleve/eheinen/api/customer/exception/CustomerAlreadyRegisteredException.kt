package com.pagaleve.eheinen.api.customer.exception

private const val MESSAGE = "The customer with CPF '%s' is already registered."

class CustomerAlreadyRegisteredException(cpf: String) : RuntimeException(MESSAGE.format(cpf))
