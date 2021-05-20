package com.pagaleve.eheinen.api.customer

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import org.springframework.stereotype.Component

private const val CPF_FIELD = "cpf"

@Component
class CustomerCustomQuery(
    private val amazonDynamoDB: AmazonDynamoDB
) {
    
    fun listCustomersPaginated(limit: Int, lastCpf: String?): ScanResultPage<Customer> =
        DynamoDBScanExpression()
            .also { 
                it.withLimit(limit)
                if (!lastCpf.isNullOrBlank()) {
                    it.withExclusiveStartKey(mapOf(CPF_FIELD to AttributeValue(lastCpf)))
                }
            }
            .run {
                DynamoDBMapper(amazonDynamoDB).scanPage(Customer::class.java, this)
            }
}