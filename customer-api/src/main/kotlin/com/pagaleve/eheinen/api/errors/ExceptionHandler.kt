package com.pagaleve.eheinen.api.errors

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.pagaleve.eheinen.api.customer.exception.CustomerAlreadyRegisteredException
import com.pagaleve.eheinen.api.customer.exception.CustomerNotFoundException
import com.pagaleve.eheinen.validators.InvalidCpfException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(value = [CustomerAlreadyRegisteredException::class])
    fun handleCustomerConflictException(e: CustomerAlreadyRegisteredException) =
        buildResponseEntity(ApiError(e.message!!), HttpStatus.CONFLICT)

    @ExceptionHandler(value = [CustomerNotFoundException::class])
    fun handleCustomerNotFoundException(e: CustomerNotFoundException) =
        buildResponseEntity(ApiError(e.message!!), HttpStatus.NOT_FOUND)

    @ExceptionHandler(
        value = [
            IllegalArgumentException::class,
            ValidationException::class,
            InvalidCpfException::class
        ]
    )
    fun handleBadRequestException(e: Exception, request: WebRequest): ResponseEntity<Any> {
        val message = Optional.ofNullable(e.message).orElse("The argument provided is invalid.")
        return buildResponseEntity(ApiError(message), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleInternalServerErrorException(e: Exception, request: WebRequest): ResponseEntity<Any> {
        val message = "An unknown error occurred"
        log.error(message, e)
        return buildResponseEntity(ApiError(message), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun handleInvalidJSONContent(e: Exception): ResponseEntity<Any> {
        val message = "Invalid JSON content: ${e.message}"
        val apiError = ApiError(message)
        return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [JsonParseException::class])
    fun handleJsonParseException(e: JsonParseException): ResponseEntity<Any> {
        return handleInvalidJSONContent(e)
    }

    @ExceptionHandler(value = [UnrecognizedPropertyException::class])
    fun handleJsonParseException(e: UnrecognizedPropertyException): ResponseEntity<Any> {
        return handleInvalidJSONContent(e)
    }

    override fun handleMissingPathVariable(
        e: MissingPathVariableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return buildResponseEntity(ApiError(e.message), HttpStatus.BAD_REQUEST)
    }

    override fun handleTypeMismatch(
        e: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(e.message!!)
        return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    override fun handleMissingServletRequestParameter(
        e: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(e.message)
        return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    override fun handleHttpMessageNotReadable(
        e: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        if (e.cause is MissingKotlinParameterException) {
            val apiError =
                ApiError("The field '${(e.cause as MissingKotlinParameterException).parameter.name}' is required.")
            return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST)
        }

        if (e.cause is InvalidFormatException) {
            val apiError = ApiError("The value informed '${(e.cause as InvalidFormatException).value}' is invalid.")
            return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST)
        }
        val apiError = ApiError("Validation error.")

        return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    private fun buildResponseEntity(apiError: ApiError, httpStatus: HttpStatus): ResponseEntity<Any> {
        return ResponseEntity(apiError, httpStatus)
    }
}
