package com.pagaleve.eheinen.api.customer

import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.pagaleve.eheinen.annotations.NoArgConstructor
import com.pagaleve.eheinen.converters.DynamoInstantToStringTypeConverter
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.Instant

@NoArgConstructor
@DynamoDBTable(tableName = "customer")
data class Customer(

    @Id
    @DynamoDBHashKey
    var cpf: String = "",

    @DynamoDBAttribute
    var email: String = "",

    @DynamoDBAttribute
    var name: String = "",

    @DynamoDBAttribute
    var phoneNumber: String = "",

    @DynamoDBAttribute
    var birthdate: String = "",

    @DynamoDBAttribute
    var profession: String = "",

    @DynamoDBAttribute
    @DynamoDBTyped(value = DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    var salary: BigDecimal = BigDecimal.ZERO,

    @DynamoDBAttribute
    var country: String = "",

    @DynamoDBAttribute
    var state: String = "",

    @DynamoDBAttribute
    var city: String = "",

    @DynamoDBAttribute
    var zipcode: String = "",

    @DynamoDBAttribute
    var street: String = "",

    @DynamoDBAttribute
    var number: String = "",

    @DynamoDBAttribute
    var complement: String = "",

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = DynamoInstantToStringTypeConverter::class)
    var createdAt: Instant = Instant.now()
)
