package com.pagaleve.eheinen.converters

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.Instant

class DynamoInstantToStringTypeConverter : DynamoDBTypeConverter<String, Instant> {

    override fun convert(time: Instant?) = time.toString()

    override fun unconvert(time: String?) = Instant.parse(time)
}