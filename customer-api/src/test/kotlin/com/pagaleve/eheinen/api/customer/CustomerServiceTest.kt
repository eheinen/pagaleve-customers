package com.pagaleve.eheinen.api.customer

import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage
import com.pagaleve.eheinen.api.customer.exception.CustomerAlreadyRegisteredException
import com.pagaleve.eheinen.api.customer.exception.CustomerNotFoundException
import com.pagaleve.eheinen.validators.CpfValidator
import com.pagaleve.eheinen.validators.InvalidCpfException
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

private const val CPF = "00000000000"
private const val EMAIL = "test@email.com"
private const val NAME = "Customer Test"
private const val PHONE_NUMBER = "+551100000000"
private const val BIRTHDATE = "01/01/1990"
private const val PROFESSION = "Software Engineer"
private val SALARY = BigDecimal.valueOf(12345.55)
private const val COUNTRY = "BRAZIL"
private const val STATE = "SÃO PAULO"
private const val CITY = "SÃO PAULO"
private const val ZIPCODE = "00000000"
private const val STREET = "RUA PAIM"
private const val NUMBER = "123"
private const val COMPLEMENT = "APARTAMENT 202"

private const val LIMIT = 2

private const val CUSTOMER_ALREADY_REGISTERED_EXCEPTION_MSG = "The customer with CPF '$CPF' is already registered."

private const val CPF_FIELD = "cpf"
private const val CUSTOMER_NOT_FOUND_EXCEPTION_MSG = "The customer with $CPF_FIELD ($CPF) could not be found."

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var customerCustomQuery: CustomerCustomQuery

    @MockK
    private lateinit var cpfValidator: CpfValidator

    @InjectMockKs
    private lateinit var customerService: CustomerService

    private fun buildCustomer() = Customer(
        CPF, EMAIL, NAME, PHONE_NUMBER, BIRTHDATE, PROFESSION, SALARY,
        COUNTRY, STATE, CITY, ZIPCODE, STREET, NUMBER, COMPLEMENT
    )

    @Nested
    inner class FindCustomerByCPFTest {

        @Test
        fun `Should find customer successfully`() {
            val customerSaved = buildCustomer()
            every { customerRepository.findById(any()) } returns Optional.of(customerSaved)

            customerService.findCustomerByCpf(CPF)

            verifyAll {
                customerRepository.findById(CPF)
            }
        }

        @Test
        fun `Should throw CustomerNotFoundException when customer is not found`() {
            every { customerRepository.findById(any()) } returns Optional.empty()

            assertThrows<CustomerNotFoundException> {
                customerService.findCustomerByCpf(CPF)
            }.also {
                assertEquals(CUSTOMER_NOT_FOUND_EXCEPTION_MSG, it.message)
            }

            verify { customerRepository.findById(CPF) }
        }
    }

    @Nested
    inner class ListCustomersTest {

        @Test
        fun `Should list customer paginated successfully when no last cpf is provided`() {
            val customerSaved1 = buildCustomer()
            val customerSaved2 = buildCustomer()
            val scanResultPage = ScanResultPage<Customer>()
            scanResultPage.results = listOf(customerSaved1, customerSaved2)

            every { customerCustomQuery.listCustomersPaginated(any(), any()) } returns scanResultPage

            customerService.listCustomers(LIMIT, null).also {
                assertEquals(customerSaved1, it[0])
                assertEquals(customerSaved2, it[1])
            }

            verifyAll {
                customerCustomQuery.listCustomersPaginated(LIMIT, null)
            }
        }

        @Test
        fun `Should list customer paginated successfully when last cpf is provided`() {
            val customerSaved = buildCustomer()
            val scanResultPage = ScanResultPage<Customer>()
            scanResultPage.results = listOf(customerSaved)

            every { customerCustomQuery.listCustomersPaginated(any(), any()) } returns scanResultPage

            customerService.listCustomers(LIMIT, CPF).also {
                assertEquals(customerSaved, it[0])
            }

            verifyAll {
                customerCustomQuery.listCustomersPaginated(LIMIT, CPF)
            }
        }
    }

    @Nested
    inner class SaveCustomerTest {

        @Test
        fun `Should save customer successfully`() {
            every { cpfValidator.validate(any()) } just Runs
            every { customerRepository.findById(any()) } returns Optional.empty()

            val customerSaved = buildCustomer()
            every { customerRepository.save<Customer>(any()) } returns customerSaved

            val customer = buildCustomer()
            customerService.saveCustomer(customer)

            verifyAll {
                cpfValidator.validate(CPF)
                customerRepository.findById(CPF)
                customerRepository.save(customer)
            }
        }

        @Test
        fun `Should throw CustomerAlreadyRegisteredException when CPF already registered`() {
            every { cpfValidator.validate(any()) } just Runs
            val customer = buildCustomer()
            every { customerRepository.findById(any()) } returns Optional.of(customer)

            assertThrows<CustomerAlreadyRegisteredException> {
                customerService.saveCustomer(customer)
            }.also {
                assertEquals(CUSTOMER_ALREADY_REGISTERED_EXCEPTION_MSG, it.message)
            }

            verifyAll {
                cpfValidator.validate(CPF)
                customerRepository.findById(CPF) 
            }
            verify(exactly = 0) { customerRepository.save(any()) }
        }

        @Test
        fun `Should throw InvalidCpfException when CPF validator throws InvalidCpfException`() {
            every { cpfValidator.validate(any()) } throws InvalidCpfException()
            val customer = buildCustomer()
            every { customerRepository.findById(any()) } returns Optional.of(customer)

            assertThrows<InvalidCpfException> {
                customerService.saveCustomer(customer)
            }

            verify {
                cpfValidator.validate(CPF)
            }
            verify(exactly = 0) { 
                customerRepository.findById(any())
                customerRepository.save(any()) 
            }
        }
    }

    @Nested
    inner class UpdateCustomerTest {

        @Test
        fun `Should update customer successfully`() {
            every { cpfValidator.validate(any()) } just Runs
            val customer = buildCustomer()
            every { customerRepository.findById(any()) } returns Optional.of(customer)
            every { customerRepository.save<Customer>(any()) } returns customer

            customerService.updateCustomer(CPF, customer)

            verifyAll {
                cpfValidator.validate(CPF)
                customerRepository.findById(CPF)
                customerRepository.save(customer)
            }
        }

        @Test
        fun `Should throw CustomerNotFoundException when customer is not found`() {
            every { cpfValidator.validate(any()) } just Runs
            every { customerRepository.findById(any()) } returns Optional.empty()

            val customer = buildCustomer()
            assertThrows<CustomerNotFoundException> {
                customerService.updateCustomer(CPF, customer)
            }.also {
                assertEquals(CUSTOMER_NOT_FOUND_EXCEPTION_MSG, it.message)
            }

            verifyAll { 
                cpfValidator.validate(CPF)
                customerRepository.findById(CPF)
            }
            verify(exactly = 0) { customerRepository.save(any()) }
        }

        @Test
        fun `Should throw InvalidCpfException when CPF validator throws InvalidCpfException`() {
            every { cpfValidator.validate(any()) } throws InvalidCpfException()
            val customer = buildCustomer()
            every { customerRepository.findById(any()) } returns Optional.of(customer)

            assertThrows<InvalidCpfException> {
                customerService.updateCustomer(CPF, customer)
            }

            verify {
                cpfValidator.validate(CPF)
            }
            verify(exactly = 0) {
                customerRepository.findById(any())
                customerRepository.save(any())
            }
        }
    }

    @Nested
    inner class DeleteCustomerTest {

        @Test
        fun `Should delete customer successfully`() {
            every { cpfValidator.validate(any()) } just Runs
            val customer = buildCustomer()
            every { customerRepository.findById(any()) } returns Optional.of(customer)
            every { customerRepository.deleteById(any()) } just Runs

            customerService.deleteCustomer(CPF)

            verifyAll {
                cpfValidator.validate(CPF)
                customerRepository.findById(CPF)
                customerRepository.deleteById(CPF)
            }
        }

        @Test
        fun `Should throw CustomerNotFoundException when customer is not found`() {
            every { cpfValidator.validate(any()) } just Runs
            every { customerRepository.findById(any()) } returns Optional.empty()

            assertThrows<CustomerNotFoundException> {
                customerService.deleteCustomer(CPF)
            }.also {
                assertEquals(CUSTOMER_NOT_FOUND_EXCEPTION_MSG, it.message)
            }

            verifyAll {
                cpfValidator.validate(CPF)    
                customerRepository.findById(CPF) 
            }
            verify(exactly = 0) { customerRepository.deleteById(any()) }
        }

        @Test
        fun `Should throw InvalidCpfException when CPF validator throws InvalidCpfException`() {
            every { cpfValidator.validate(any()) } throws InvalidCpfException()
            val customer = buildCustomer()
            every { customerRepository.findById(any()) } returns Optional.of(customer)

            assertThrows<InvalidCpfException> {
                customerService.deleteCustomer(CPF)
            }

            verify {
                cpfValidator.validate(CPF)
            }
            verify(exactly = 0) {
                customerRepository.findById(any())
                customerRepository.deleteById(any())
            }
        }
    }
}