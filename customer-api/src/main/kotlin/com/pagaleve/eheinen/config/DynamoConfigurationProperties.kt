package com.pagaleve.eheinen.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class DynamoConfigurationProperties(
    
    @Value("\${aws.dynamodb.endpoint}") val endpoint: String
)
