package com.pagaleve.eheinen.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class AWSConfigurationProperties(

    @Value("\${aws.region}") val region: String,
    
    @Value("\${aws.access-key}") val accessKey: String,
    
    @Value("\${aws.secret-access-key}") val secretAccessKey: String,
)
