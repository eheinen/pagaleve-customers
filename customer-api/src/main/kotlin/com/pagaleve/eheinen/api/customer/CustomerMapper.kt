package com.pagaleve.eheinen.api.customer

import org.mapstruct.Mapper

@Mapper
interface CustomerMapper {
    
    fun mapToDto(customer: Customer): CustomerDto

    fun mapToModel(customerDto: CustomerDto): Customer
}
