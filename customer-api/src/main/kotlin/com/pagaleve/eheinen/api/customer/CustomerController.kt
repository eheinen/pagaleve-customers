package com.pagaleve.eheinen.api.customer

import com.pagaleve.eheinen.api.ApiPathVariables
import com.pagaleve.eheinen.api.ApiParams
import com.pagaleve.eheinen.api.ApiResources
import com.pagaleve.eheinen.api.ApiVersions
import org.mapstruct.factory.Mappers
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("${ApiVersions.API_1}${ApiResources.CUSTOMERS}")
class CustomerController(
    private val customerService: CustomerService
) {
    private val customerConverter = Mappers.getMapper(CustomerMapper::class.java)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid customerDto: CustomerDto): Unit =
        customerConverter
            .mapToModel(customerDto)
            .run { customerService.saveCustomer(this) }

    @PutMapping(ApiPathVariables.CPF)
    fun updateCustomer(
        @PathVariable(ApiParams.CPF) cpf: String,
        @RequestBody @Valid customerDto: CustomerDto
    ): Unit =
        customerConverter
            .mapToModel(customerDto)
            .run { customerService.updateCustomer(cpf, this) }

    @DeleteMapping(ApiPathVariables.CPF)
    fun deleteCustomer(@PathVariable(ApiParams.CPF) cpf: String) = customerService.deleteCustomer(cpf)

    @GetMapping(ApiPathVariables.CPF)
    fun findCustomerByCpf(@PathVariable(ApiParams.CPF) cpf: String) =
        customerService
            .findCustomerByCpf(cpf)
            .let { customerConverter.mapToDto(it) }

    @GetMapping
    fun listCustomers(
        @RequestParam(ApiParams.LIMIT) limit: Int, 
        @RequestParam(ApiParams.LAST_CPF) lastCpf: String?
    ): List<CustomerDto> =
        customerService
            .listCustomers(limit, lastCpf)
            .map { customerConverter.mapToDto(it) }
}