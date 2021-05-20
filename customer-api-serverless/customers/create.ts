'use strict'

import {DynamoDB} from 'aws-sdk'
import customer from "./customer";

const dynamoDb = new DynamoDB.DocumentClient()

module.exports.create = (event, context, callback) => {
    const requestBody: customer = JSON.parse(event.body);
    
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Item: requestBody
    }

    dynamoDb.put(params, (error) => {
        if (error) {
            console.error(error)
            callback(new Error('Couldn\'t create customer.'))
            return
        }

        const response = {
            statusCode: 201,
            body: JSON.stringify(params.Item)
        }
        callback(null, response)
    })
}