package com.pagaleve.eheinen.validators

import org.springframework.stereotype.Component

@Component
class CpfValidator {
    
    fun validate(cpf: String) {
        // Validate if CPF is valid and exists
        if (false) {
            throw InvalidCpfException()
        }
    }
}