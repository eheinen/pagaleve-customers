'use strict'

import {DynamoDB} from 'aws-sdk'
import customer from "./customer";

const dynamoDb = new DynamoDB.DocumentClient()

module.exports.update = (event, context, callback) => {
    const requestBody: customer = JSON.parse(event.body);
    
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            id: event.pathParameters.cpf,
        },
        ExpressionAttributeNames: {
            '#name': 'name',
        },
        ExpressionAttributeValues: {
            ':email': requestBody.email,
            ':phoneNumber': requestBody.phoneNumber,
            // ...
        },
        UpdateExpression: 'SET #name = :name, email = :email, phoneNumber = :phoneNumber',
        ReturnValues: 'ALL_NEW',
    };

    dynamoDb.update(params, (error, result) => {
        if (error) {
            console.error(error);
            callback(null, {
                statusCode: error.statusCode || 501,
                headers: {'Content-Type': 'text/plain'},
                body: 'Couldn\'t update customer.',
            });
            return;
        }

        // create a response
        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Attributes),
        };
        callback(null, response);
    })
}