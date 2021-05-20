package com.pagaleve.eheinen.api.customer

import com.pagaleve.eheinen.api.customer.exception.CustomerAlreadyRegisteredException
import com.pagaleve.eheinen.api.customer.exception.CustomerNotFoundException
import com.pagaleve.eheinen.validators.CpfValidator
import org.springframework.stereotype.Service

private const val CPF_FIELD = "cpf"

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val customerCustomQuery: CustomerCustomQuery,
    private val cpfValidator: CpfValidator
) {

    fun findCustomerByCpf(cpf: String): Customer =
        customerRepository.findById(cpf).orElseThrow { CustomerNotFoundException(CPF_FIELD, cpf) }

    fun listCustomers(limit: Int, lastCpf: String?): List<Customer> =
        customerCustomQuery.listCustomersPaginated(limit, lastCpf).results

    fun saveCustomer(customer: Customer) {
        cpfValidator.validate(customer.cpf)
        customerRepository
            .findById(customer.cpf)
            .ifPresent { throw CustomerAlreadyRegisteredException(customer.cpf) }

        customerRepository.save(customer)
    }

    fun updateCustomer(cpf: String, customerToUpdate: Customer) {
        cpfValidator.validate(cpf)
        customerRepository
            .findById(cpf)
            .orElseThrow { CustomerNotFoundException(CPF_FIELD, cpf) }
            .also {
                it.email = customerToUpdate.email
                it.name = customerToUpdate.name
                it.phoneNumber = customerToUpdate.phoneNumber
                it.birthdate = customerToUpdate.birthdate
                it.profession = customerToUpdate.profession
                it.salary = customerToUpdate.salary
                it.country = customerToUpdate.country
                it.state = customerToUpdate.state
                it.city = customerToUpdate.city
                it.zipcode = customerToUpdate.zipcode
                it.street = customerToUpdate.street
                it.number = customerToUpdate.number
                it.complement = customerToUpdate.complement
            }.run { 
                customerRepository.save(this) 
            }
    }

    fun deleteCustomer(cpf: String) {
        cpfValidator.validate(cpf)
        customerRepository
            .findById(cpf)
            .orElseThrow { CustomerNotFoundException(CPF_FIELD, cpf) }
            .also {
                customerRepository.deleteById(cpf)
            }
    }
}