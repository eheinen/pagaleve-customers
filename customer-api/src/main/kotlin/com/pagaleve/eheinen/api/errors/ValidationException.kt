package com.pagaleve.eheinen.api.errors

private const val MESSAGE = "There was a problem with: '%s', reason: '%s'."

class ValidationException(field: String, reason: String) : RuntimeException(MESSAGE.format(field, reason))
