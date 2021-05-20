package com.pagaleve.eheinen.api.customer

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.pagaleve.eheinen.PagaLeveApplication
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

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

private const val URL = "/api/1/customers"

private const val DYNAMODB_PORT = 8000
private const val DYNAMODB_DOCKER_IMAGE = "amazon/dynamodb-local"

//@Profile("e2e")
//@ExtendWith(SpringExtension::class)
//@SpringBootTest(classes = [PagaLeveApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerE2eTest @Autowired constructor(
//    private val customerRepository: CustomerRepository,
//    private val restTemplate: TestRestTemplate
){    
//    private fun buildCustomer() = Customer(
//        CPF, EMAIL, NAME, PHONE_NUMBER, BIRTHDATE, PROFESSION, SALARY,
//        COUNTRY, STATE, CITY, ZIPCODE, STREET, NUMBER, COMPLEMENT
//    )
    
//    @BeforeEach
//    fun beforeEach() {
//        customerRepository.deleteAll()
//    }
    
    @Nested
    inner class CreateCustomer {
        
        @Test
        fun `Should create customer and return status code 201`() {
//            val request = HttpEntity(buildCustomer())
//            restTemplate
//                .postForEntity(URL, request, Unit::class.java)
//                .also {
//                    assertEquals(HttpStatus.CREATED, it.statusCode)
//                }
//
//            customerRepository
//                .findById(CPF)
//                .get()
//                .also {
//                    assertEquals(CPF, it.cpf)
//                    assertEquals(NAME, it.name)
//                    // Check all fields
//                }
        }

        @Test
        fun `Should not create customer and return status code 409 when CPF is already registered`() {
        }

        @Test
        fun `Should not create customer and return status code 409 when email is already registered`() {
        }

        @Test
        fun `Should not create customer and return status code 409 when phone number is already registered`() {
        }

        @Test
        fun `Should not create customer and return status code 400 when CPF is invalid`() {
        }

        @Test
        fun `Should not create customer and return status code 400 when required fields are not provided`() {
        }

        @Test
        fun `Should not create customer and return status code 500 when an internal problem happens`() {
        }

        @Test
        fun `Should not create customer and return status code 502 when an external service is not working`() {
        }

        @Test
        fun `Should return status code 401 when request is unauthorized`() {
        }

        @Test
        fun `Should return status code 403 when request is forbidden`() {
        }
    }

    @Nested
    inner class UpdateCustomer {

        @Test
        fun `Should update customer and return status code 200`() {
        }

        @Test
        fun `Should not update customer and return status code 409 when email is already registered`() {
        }

        @Test
        fun `Should not update customer and return status code 409 when phone number is already registered`() {
        }

        @Test
        fun `Should not update customer and return status code 400 when email is invalid`() {
        }

        @Test
        fun `Should not update customer and return status code 400 when required fields are not provided`() {
        }

        @Test
        fun `Should not update customer and return status code 500 when an internal problem happens`() {
        }

        @Test
        fun `Should not update customer and return status code 502 when an external service is not working`() {
        }

        @Test
        fun `Should return status code 401 when request is unauthorized`() {
        }

        @Test
        fun `Should return status code 403 when request is forbidden`() {
        }
    }

    @Nested
    inner class FindCustomerByCPF {

        @Test
        fun `Should return customer and status code 200`() {
        }

        @Test
        fun `Should return status code 404 when customer is not found`() {
        }
        
        @Test
        fun `Should return status code 500 when an internal problem happens`() {
        }

        @Test
        fun `Should return status code 502 when an external service is not working`() {
        }

        @Test
        fun `Should return status code 401 when request is unauthorized`() {
        }

        @Test
        fun `Should return status code 403 when request is forbidden`() {
        }
    }

    @Nested
    inner class ListCustomers {

        @Test
        fun `Should return customer a paginated list of customers and status code 200`() {
        }

        @Test
        fun `Should return empty list when no customer is found`() {
        }

        @Test
        fun `Should return status code 500 when an internal problem happens`() {
        }

        @Test
        fun `Should return status code 502 when an external service is not working`() {
        }

        @Test
        fun `Should return status code 401 when request is unauthorized`() {
        }

        @Test
        fun `Should return status code 403 when request is forbidden`() {
        }
    }

    @Nested
    inner class DeleteCustomer {

        @Test
        fun `Should delete customer and return status code 200`() {
        }

        @Test
        fun `Should not delete customer and return status code 404 when customer is not found`() {
        }

        @Test
        fun `Should not delete customer and return status code 500 when an internal problem happens`() {
        }

        @Test
        fun `Should not delete customer and return status code 502 when an external service is not working`() {
        }

        @Test
        fun `Should return status code 401 when request is unauthorized`() {
        }

        @Test
        fun `Should return status code 403 when request is forbidden`() {
        }
    }
}