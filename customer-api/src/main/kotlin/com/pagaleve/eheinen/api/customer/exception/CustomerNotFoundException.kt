package com.pagaleve.eheinen.api.customer.exception

private const val MESSAGE = "The customer with %s (%s) could not be found."

class CustomerNotFoundException(field: String, value: String): RuntimeException(MESSAGE.format(field, value))