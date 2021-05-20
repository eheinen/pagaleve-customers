package com.pagaleve.eheinen.api.errors

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
data class ApiError(val message: String = "") {
    var violations: List<ConstraintViolation>? = null
}
