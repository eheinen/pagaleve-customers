package com.pagaleve.eheinen.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.pagaleve.eheinen.api.customer.CustomerRepository
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = [CustomerRepository::class])
class DynamoConfiguration(
    private val awsCredentialsProvider: AWSCredentialsProvider,
    private val awsConfigurationProperties: AWSConfigurationProperties,
    private val dynamoConfigurationProperties: DynamoConfigurationProperties
) {

    @Bean("amazonDynamoDB")
    @Profile("default")
    fun amazonDynamoDB(): AmazonDynamoDB =
        AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(awsCredentialsProvider)
            .withEndpointConfiguration(
                AwsClientBuilder
                    .EndpointConfiguration(
                        dynamoConfigurationProperties.endpoint,
                        awsConfigurationProperties.region
                    )
            ).build()

    @Bean("amazonDynamoDB")
    @Profile("e2e")
    fun amazonDynamoDBLocal(): AmazonDynamoDB =
        AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(awsCredentialsProvider)
            .withEndpointConfiguration(
                AwsClientBuilder
                    .EndpointConfiguration(
                        dynamoConfigurationProperties.endpoint,
                        awsConfigurationProperties.region
                    )
            ).build()
}