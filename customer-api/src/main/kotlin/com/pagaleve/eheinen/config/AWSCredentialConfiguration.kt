package com.pagaleve.eheinen.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSCredentialConfiguration(
    private val awsConfigurationProperties: AWSConfigurationProperties
) {

    @Bean
    fun awsCredentialsProvider(): AWSCredentialsProvider =
        AWSStaticCredentialsProvider(
            BasicAWSCredentials(
                awsConfigurationProperties.accessKey,
                awsConfigurationProperties.secretAccessKey
            )
        )
}